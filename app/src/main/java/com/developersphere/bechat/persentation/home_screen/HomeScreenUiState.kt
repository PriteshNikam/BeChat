package com.developersphere.bechat.persentation.home_screen

import android.bluetooth.BluetoothDevice
import com.developersphere.bechat.domain.models.Message

data class HomeScreenUiState(
    var isBluetoothEnable: Boolean = false,
    var pairedDevices: List<BluetoothDevice>? = emptyList<BluetoothDevice>(),
    var availableDevices: List<BluetoothDevice>? = emptyList<BluetoothDevice>(),
    var isDiscovering: Boolean = false,
    val connectedDevice: BluetoothDevice? = null,   // currently connected device
    val chatMessages: List<Message> = emptyList(), // chat history
    var isServer: Boolean = false,      // role (server/client)
    var isConnected: Boolean = false,
    var isConnecting: Boolean = false,
    var errorMessage: String? = null,
)
