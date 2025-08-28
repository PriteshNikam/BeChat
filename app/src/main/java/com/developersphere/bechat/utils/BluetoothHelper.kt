package com.developersphere.bechat.utils

import android.Manifest
import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Build
import android.os.Parcelable
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.core.content.ContextCompat
import com.developersphere.bechat.domain.enums.BondState
import com.developersphere.bechat.domain.models.Device
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@SuppressLint("MissingPermission")
class BluetoothHelper @Inject constructor(@ApplicationContext private val context: Context) {
    val bluetoothManager: BluetoothManager = context.getSystemService(BluetoothManager::class.java)
    val bluetoothAdapter: BluetoothAdapter? = bluetoothManager.adapter

    private val _isBluetoothActive = MutableStateFlow(bluetoothAdapter?.isEnabled == true)
    var isBluetoothActive: StateFlow<Boolean> = _isBluetoothActive
    private var onBluetoothEnabledCallback: (() -> Unit)? = null

    private val _isDiscovering = MutableStateFlow(bluetoothAdapter?.isDiscovering == true)
    var isDiscovering: StateFlow<Boolean> = _isDiscovering

    private var _scannedDevices = MutableStateFlow<List<Device>>(emptyList())
    val scannedDevices = _scannedDevices

    private var receiver: BluetoothReceiver? = BluetoothReceiver { device ->
        _scannedDevices.update { devices ->
            val newDevice = Device(name = device.name ?: "Unknown device", address = device.address)
            (if (devices.any { it.address == newDevice.address }) {
                devices
            } else {
                devices + newDevice
            })
        }
    }


    fun enableBluetooth(
        launcher: ActivityResultLauncher<Intent>,
        onEnabled: () -> Unit,
    ) {
        onBluetoothEnabledCallback = onEnabled
        val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
        launcher.launch(enableBtIntent)
    }

    fun getPairedDevices(): List<Device> {
        val devices: Set<BluetoothDevice>? = bluetoothAdapter?.bondedDevices

        val connectedDevices = mutableListOf<Device>()

        if (!devices.isNullOrEmpty()) {
            for (device in devices) {
                connectedDevices.add(
                    Device(
                        name = device.name,
                        address = device.address,
                        bondState = if (device.bondState == BluetoothDevice.BOND_BONDED)
                            BondState.PAIRED else BondState.UNPAIRED,
                        type = device.type
                    )
                )
            }
        }
        return connectedDevices
    }


    fun startDiscovery() {
        if (hasPermission(Manifest.permission.BLUETOOTH_SCAN) &&
            hasPermission(Manifest.permission.ACCESS_FINE_LOCATION)
        ) {
            Log.d("BluetoothVM", "Ra1 BLUETOOTH_SCAN permission granted")
            Log.d("BluetoothVM", "Ra1 isEnable ${bluetoothAdapter?.isEnabled}")
            // Register receiver dynamically
            val filter = IntentFilter().apply {
                addAction(BluetoothDevice.ACTION_FOUND)
                addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED)
                addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED)
            }
            context.registerReceiver(receiver, filter)

            if (bluetoothAdapter?.isDiscovering == true) {
                bluetoothAdapter.cancelDiscovery()
            }
            val started = bluetoothAdapter?.startDiscovery() ?: false
            Log.d("BluetoothVM", "Ra1 Discovery started: $started")
        } else {
            // Optionally, notify UI that permission is missing
            Log.d("BluetoothVM", "Ra1 BLUETOOTH_SCAN permission not granted")
        }
    }

    fun stopDiscovery() {
        if (hasPermission(Manifest.permission.BLUETOOTH_SCAN)) {
            if (bluetoothAdapter?.isDiscovering == true) {
                bluetoothAdapter.cancelDiscovery()
            }
            runCatching { context.unregisterReceiver(receiver) }
        } else {
            // Optionally, notify UI that permission is missing
            Log.d("BluetoothVM", "Ra1 BLUETOOTH_SCAN permission not granted")
        }

    }

    fun hasPermission(permission: String): Boolean {
        return ContextCompat.checkSelfPermission(
            context,
            permission
        ) == PackageManager.PERMISSION_GRANTED
    }
}

@Suppress("DEPRECATION")
fun <T : Parcelable> Intent.getParcelableCompat(key: String, clazz: Class<T>): T? {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        getParcelableExtra(key, clazz)
    } else {
        getParcelableExtra(key) as? T
    }
}