package com.developersphere.bechat.data.receiver

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.developersphere.bechat.utils.getParcelableCompat

class FoundDeviceReceiver(
    private val onDeviceFound: (BluetoothDevice) -> Unit,
    private val onStatusChanged: (Boolean) -> Unit
) : BroadcastReceiver() {

    @SuppressLint("MissingPermission")
    override fun onReceive(context: Context?, intent: Intent?) {
        when (intent?.action) {
            BluetoothDevice.ACTION_FOUND -> {
                val device: BluetoothDevice? = intent.getParcelableCompat(
                    key = BluetoothDevice.EXTRA_DEVICE,
                    clazz = BluetoothDevice::class.java
                )
                device?.let {
                    onDeviceFound(device)
                }
            }

            BluetoothAdapter.ACTION_DISCOVERY_STARTED -> {
                Log.d("BluetoothReceiver", "Ra1 ACTION_DISCOVERY_STARTED")
                onStatusChanged(true)
            }

            BluetoothAdapter.ACTION_DISCOVERY_FINISHED -> {
                Log.d("BluetoothReceiver", "Ra1 ACTION_DISCOVERY_FINISHED")
                onStatusChanged(false)
            }

        }
    }
}