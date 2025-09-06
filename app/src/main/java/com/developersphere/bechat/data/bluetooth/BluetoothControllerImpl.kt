package com.developersphere.bechat.data.bluetooth

import com.developersphere.bechat.data.receiver.ConnectionStateReceiver
import com.developersphere.bechat.data.receiver.FoundDeviceReceiver
import android.Manifest
import android.annotation.SuppressLint
import android.bluetooth.*
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.core.content.ContextCompat
import com.developersphere.bechat.data.receiver.ScanningStatusReceiver
import com.developersphere.bechat.data.service.BluetoothDataService
import com.developersphere.bechat.data.toByteArray
import com.developersphere.bechat.domain.bluetooth.BluetoothController
import com.developersphere.bechat.domain.bluetooth.ConnectionResult
import com.developersphere.bechat.domain.models.Message
import com.developersphere.bechat.domain.models.MessageStatus
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import java.io.IOException
import java.io.OutputStream
import java.util.UUID
import javax.inject.Inject
import kotlin.random.Random

// create interface for this helper class.
@SuppressLint("MissingPermission")
class BluetoothControllerImpl @Inject constructor(
    @ApplicationContext private val context: Context,
) : BluetoothController {

    companion object {
        private val APP_UUID: UUID =
            UUID.fromString("00001101-0000-1000-8000-00805F9B34FB") // SPP UUID
        private const val APP_NAME = "BeChat"
    }

    private val bluetoothManager: BluetoothManager =
        context.getSystemService(BluetoothManager::class.java)
    private val bluetoothAdapter: BluetoothAdapter? = bluetoothManager.adapter

    private var onBluetoothEnabledCallback: (() -> Unit)? = null

    private val _isDiscovering = MutableStateFlow(bluetoothAdapter?.isDiscovering == true)
    override var isDiscovering: StateFlow<Boolean> = _isDiscovering

    private var bluetoothDataTransferService: BluetoothDataService? = null

    private var _isBluetoothActive = MutableStateFlow(bluetoothAdapter?.isEnabled == true)
    override val isBluetoothActive: StateFlow<Boolean>
        get() = _isBluetoothActive.asStateFlow()

    private var _scannedDevices = MutableStateFlow<List<BluetoothDevice>>(emptyList())
    override val scannedDevices: StateFlow<List<BluetoothDevice>>
        get() = _scannedDevices.asStateFlow()

    private var _connectedDevices = MutableStateFlow<List<BluetoothDevice>?>(emptyList())
    override val connectedDevices: StateFlow<List<BluetoothDevice>?>
        get() = _connectedDevices

    private val _error = MutableSharedFlow<String>()
    override val error: SharedFlow<String>
        get() = _error.asSharedFlow()

    private var serverSocket: BluetoothServerSocket? = null
    private var clientSocket: BluetoothSocket? = null
    private var writer: OutputStream? = null

    private var _isConnected = MutableStateFlow(false)
    override val isConnected: StateFlow<Boolean>
        get() =
            _isConnected.asStateFlow()


    // device broadcast receiver
    private val foundDeviceReceiver: FoundDeviceReceiver? = FoundDeviceReceiver(
        onDeviceFound = { device ->
            _scannedDevices.update { devices ->
                // dont add the device if it is already in the list or if it is bonded.
                if (shouldAddDevice(device, devices)) {
                    devices + device
                } else {
                    devices
                }
            }
        },
        onStatusChanged = { status ->
            _isDiscovering.update { status }
        }
    )

    fun shouldAddDevice(device: BluetoothDevice, currentList: List<BluetoothDevice>?): Boolean {
        if (currentList?.any { it.address == device.address } == true || device.bondState == BluetoothDevice.BOND_BONDED) {
            return false
        }

        val bluetoothClass = device.bluetoothClass
        return bluetoothClass.majorDeviceClass == BluetoothClass.Device.Major.PHONE
    }

    // print device type.
    private fun printDeviceType(device: BluetoothDevice) {
        val bluetoothClass = device.bluetoothClass

        var deviceType = when (bluetoothClass.majorDeviceClass) {
            BluetoothClass.Device.Major.COMPUTER -> "Computer/Laptop"
            BluetoothClass.Device.Major.PHONE -> "Phone"
            BluetoothClass.Device.Major.AUDIO_VIDEO -> "Audio/Video Device"
            BluetoothClass.Device.Major.WEARABLE -> "Wearable"
            BluetoothClass.Device.Major.IMAGING -> "Imaging Device"
            BluetoothClass.Device.Major.NETWORKING -> "Networking Device"
            BluetoothClass.Device.Major.PERIPHERAL -> "Peripheral (Keyboard/Mouse)"
            BluetoothClass.Device.Major.TOY -> "Toy"
            BluetoothClass.Device.Major.HEALTH -> "Health Device"
            else -> "Unknown"
        }
        Log.d("BluetoothHelper", "deviceType: $deviceType")
    }

    private val connectionStateReceiver: ConnectionStateReceiver? =
        ConnectionStateReceiver { isConnected, device ->
            if (bluetoothAdapter?.bondedDevices?.contains(device) == true) {
                _isConnected.update { isConnected }
//            _connectedDevices.update { devices ->
//
//            }
            } else {
                CoroutineScope(Dispatchers.IO).launch {
                    _error.emit("Can't connect to a non-paired device.")
                }
            }
        }

    init {
        context.registerReceiver(
            connectionStateReceiver,
            IntentFilter().apply {
                addAction(BluetoothDevice.ACTION_ACL_CONNECTED)
                addAction(BluetoothDevice.ACTION_ACL_DISCONNECTED)
                addAction(BluetoothAdapter.ACTION_CONNECTION_STATE_CHANGED)
            }
        )
    }


    fun enableBluetooth(
        launcher: ActivityResultLauncher<Intent>,
        onEnabled: () -> Unit,
    ) {
        onBluetoothEnabledCallback = onEnabled
        val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
        launcher.launch(enableBtIntent)
    }

    fun getPairedDevices(): List<BluetoothDevice> {
        val devices: Set<BluetoothDevice>? = bluetoothAdapter?.bondedDevices
        val pairedDevices = mutableListOf<BluetoothDevice>()

        if (!devices.isNullOrEmpty()) {
            devices.forEach { device ->
                // show only phone paired devices.
                if (device.bluetoothClass.majorDeviceClass == BluetoothClass.Device.Major.PHONE) {
                    pairedDevices.add(device)
                }
            }
        }
        return pairedDevices
    }

    override fun startDiscovering() {
        if (hasPermission(Manifest.permission.BLUETOOTH_SCAN) &&
            hasPermission(Manifest.permission.ACCESS_FINE_LOCATION)
        ) {
            Log.d("BluetoothVM", "Ra1 BLUETOOTH_SCAN permission granted")

            val scanFilter = IntentFilter().apply {
                addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED)
                addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED)
            }

            // Register receiver dynamically
            val filter = IntentFilter().apply {
                addAction(BluetoothDevice.ACTION_FOUND)
                addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED)
                addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED)
            }
            context.registerReceiver(foundDeviceReceiver, filter)

            if (bluetoothAdapter?.isDiscovering == true) {
                bluetoothAdapter.cancelDiscovery()
            }
            bluetoothAdapter?.startDiscovery()
        } else {
            // Optionally, notify UI that permission is missing
            Log.d("BluetoothVM", "Ra1 BLUETOOTH_SCAN permission not granted")
        }
    }

    override fun stopDiscovering() {
        if (hasPermission(Manifest.permission.BLUETOOTH_SCAN)) {
            if (bluetoothAdapter?.isDiscovering == true) {
                bluetoothAdapter.cancelDiscovery()
                _isDiscovering.value = false
            }
            runCatching { context.unregisterReceiver(foundDeviceReceiver) }
        } else {
            // Optionally, notify UI that permission is missing
            Log.d("BluetoothVM", "BLUETOOTH_SCAN permission not granted")
        }
    }

    fun hasPermission(permission: String): Boolean {
        return ContextCompat.checkSelfPermission(
            context,
            permission
        ) == PackageManager.PERMISSION_GRANTED
    }

    override fun startServer(): Flow<ConnectionResult> {
        return flow {
            if (!hasPermission(Manifest.permission.BLUETOOTH_CONNECT)) {
                Log.d("BeChat", "no bluetooth permission")
            }

            serverSocket = bluetoothAdapter?.listenUsingRfcommWithServiceRecord(APP_NAME, APP_UUID)

            var loop = true
            while (loop) {
                clientSocket = try {
                    serverSocket?.accept()
                } catch (exception: Exception) {
                    emit(ConnectionResult.Error(exception.message ?: "Connection failed"))
                    loop = false
                    null
                }
                emit(ConnectionResult.ConnectionEstablished)
                clientSocket?.let { it ->
                    serverSocket?.close()
                    val service = BluetoothDataService(it)
                    bluetoothDataTransferService = service

                    emitAll(service.listenForMessages())
                }
            }
        }.onCompletion {
            closeConnection()
        }.flowOn(Dispatchers.IO)
    }

    override fun connectDevice(device: BluetoothDevice): Flow<ConnectionResult> {
        return flow {
            if (!hasPermission(Manifest.permission.BLUETOOTH_CONNECT)) {
                throw SecurityException("No BLUETOOTH_CONNECT permission")
            }

            clientSocket =
                bluetoothAdapter?.getRemoteDevice(device.address)
                    ?.createRfcommSocketToServiceRecord(
                        APP_UUID
                    )

            if (bluetoothAdapter?.bondedDevices?.contains(device) == true) {
            }

            stopDiscovering()

            clientSocket?.let { socket ->
                try {
                    socket.connect()
                    emit(ConnectionResult.ConnectionEstablished)

                    BluetoothDataService(socket).also {
                        bluetoothDataTransferService = it
                        emitAll(
                            it.listenForMessages()
                        )
                    }
                } catch (e: IOException) {
                    socket.close()
                    clientSocket = null
                    emit(ConnectionResult.Error(e.message ?: "Connection failed"))
                }
            }
        }.onCompletion {
            closeConnection()
        }.flowOn(Dispatchers.IO)
    }

    override fun closeConnection() {
        serverSocket?.close()
        clientSocket?.close()
        serverSocket = null
        clientSocket = null
    }

    override fun release() {
        context.unregisterReceiver(foundDeviceReceiver)
        context.unregisterReceiver(connectionStateReceiver)
        closeConnection()
    }

    override suspend fun sendMessage(message: String): Message? {
        var msgStatus: Boolean? = null
        try {
            if (!hasPermission(Manifest.permission.BLUETOOTH_CONNECT)) {
                Log.d("", "Ra1 No permission")
                return null
            }

            if (bluetoothDataTransferService == null) {
                Log.d("", "Ra1 data transfer service not init")
                msgStatus = false
            }

            val messageToSend = Message(
                message = message,
                msgId = Random.nextInt(),
                isSentByUser = true,
                senderName = bluetoothAdapter?.name ?: "Unknown name"
            )

            msgStatus = bluetoothDataTransferService?.sendMessage(messageToSend.toByteArray())
            Log.d("BeChat", "Ra1 controller msg status -> $msgStatus")
            if (msgStatus == true) {
                return messageToSend.copy(
                    status = MessageStatus.SENT
                )
            } else {
                return messageToSend.copy(
                    status = MessageStatus.FAILED
                )
            }
        } catch (e: IOException) {
            Log.e("BLE", "Send failed: ${e.message}")
            return Message(msgId = 0, message = "Couldn't send message")
        }
        return null
    }
}