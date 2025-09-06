package com.developersphere.bechat.domain.bluetooth

import android.bluetooth.BluetoothDevice
import com.developersphere.bechat.domain.models.Message
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

interface BluetoothController {
    val isConnected: StateFlow<Boolean>
    val isDiscovering: StateFlow<Boolean>
    val scannedDevices: StateFlow<List<BluetoothDevice>>
    val connectedDevices: StateFlow<List<BluetoothDevice>?>
    val isBluetoothActive: StateFlow<Boolean>
    val error: SharedFlow<String>

    fun startDiscovering()
    fun startServer(): Flow<ConnectionResult>
    fun stopServer()
    fun connectDevice(device: BluetoothDevice): Flow<ConnectionResult>
    fun closeConnection()
    fun stopDiscovering()
    suspend fun sendMessage(message:String): Message?
    fun release()
}