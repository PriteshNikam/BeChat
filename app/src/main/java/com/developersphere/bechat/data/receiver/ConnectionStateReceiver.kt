package com.developersphere.bechat.data.receiver

import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log

class ConnectionStateReceiver(
    private val onStateChanged: (isConnected: Boolean, BluetoothDevice) -> Unit,
) : BroadcastReceiver() {

    @SuppressLint("MissingPermission")
    override fun onReceive(context: Context?, intent: Intent?) {
        val device: BluetoothDevice? =
            if (Build.VERSION.SDK_INT == Build.VERSION_CODES.TIRAMISU) {
                intent?.getParcelableExtra(
                    BluetoothDevice.EXTRA_DEVICE,
                    BluetoothDevice::class.java
                )
            } else {
                intent?.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE)
            }

        when (intent?.action) {
            BluetoothDevice.ACTION_ACL_CONNECTED -> {
                onStateChanged(true, device ?: return)
            }

            BluetoothDevice.ACTION_ACL_DISCONNECTED -> {
                Log.d("BluetoothReceiver", "ACTION_DISCOVERY_FINISHED")
                onStateChanged(false, device ?: return)
            }
        }
    }
}
