package com.developersphere.bechat.persentation.home_screen

import com.developersphere.bechat.domain.models.Device

data class HomeScreenUiState(
    var isBluetoothEnable: Boolean = false,
    var pairedDevices: List<Device>? = emptyList<Device>(),
    var availableDevices: List<Device>? = emptyList<Device>(),
    var isDiscovering: Boolean = false,
)
