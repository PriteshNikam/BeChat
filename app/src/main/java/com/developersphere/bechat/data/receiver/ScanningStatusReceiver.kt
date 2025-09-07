package com.developersphere.bechat.data.receiver

import android.bluetooth.BluetoothAdapter
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class ScanningStatusReceiver(
    val onStatusChange: (Boolean) -> Unit,
) :
    BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        when (intent?.action) {
            BluetoothAdapter.ACTION_DISCOVERY_STARTED -> {
                Log.d("BeChat", "Ra1 ACTION_DISCOVERY_STARTED")
                onStatusChange(true)
            }

            BluetoothAdapter.ACTION_DISCOVERY_FINISHED -> {
                Log.d("BeChat", "Ra1 ACTION_DISCOVERY_FINISHED")
                onStatusChange(false)
            }
        }
    }
}