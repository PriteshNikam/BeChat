package com.developersphere.bechat.utils

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.content.Context
import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

class BluetoothHelper @Inject constructor(@ApplicationContext private val context: Context) {
    val bluetoothManager: BluetoothManager = context.getSystemService(BluetoothManager::class.java)
    val bluetoothAdapter: BluetoothAdapter? = bluetoothManager.adapter

    private val _isBluetoothActive = MutableStateFlow(bluetoothAdapter?.isEnabled == true)
    var isBluetoothActive = _isBluetoothActive.value
    private var onBluetoothEnabledCallback: (() -> Unit)? = null


    fun enableBluetooth(
        launcher: ActivityResultLauncher<Intent>,
        onEnabled: () -> Unit
    ) {
        onBluetoothEnabledCallback = onEnabled
        val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
        launcher.launch(enableBtIntent)
    }
}