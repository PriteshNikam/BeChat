package com.developersphere.bechat.utils

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import com.developersphere.bechat.domain.enums.BondState
import com.developersphere.bechat.domain.models.Device

class BluetoothReceiver(
    private val onDeviceFound: (Device) -> Unit,
) : BroadcastReceiver() {

    @SuppressLint("MissingPermission")
    override fun onReceive(context: Context?, intent: Intent?) {
        Log.d("BluetoothReceiver", "Ra1 onReceive :: ${intent?.action}")
        when (intent?.action) {
            BluetoothDevice.ACTION_FOUND -> {
                val device: BluetoothDevice? =
                    if (Build.VERSION.SDK_INT == Build.VERSION_CODES.TIRAMISU) {
                        intent.getParcelableExtra(
                            BluetoothDevice.EXTRA_DEVICE,
                            BluetoothDevice::class.java
                        )
                    } else {
                        intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE)
                    }
                Log.d("BluetoothReceiver", "Ra1 ACTION_FOUND :: ${device}")

                device?.let {
                    val device = Device(
                        device.name,
                        device.address,
                        bondState = if (device.bondState == BluetoothDevice.BOND_BONDED)
                            BondState.PAIRED else BondState.UNPAIRED
                    )
                    onDeviceFound(device)
                }
            }

            BluetoothAdapter.ACTION_DISCOVERY_FINISHED -> {
                Log.d("BluetoothReceiver", "Ra1 ACTION_DISCOVERY_FINISHED")
            }
        }
    }

}