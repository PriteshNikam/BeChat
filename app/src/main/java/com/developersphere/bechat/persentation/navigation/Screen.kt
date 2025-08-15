package com.developersphere.bechat.persentation.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class Screen(val route: String) {
    @Serializable
    data object BluetoothPermissionScreen : Screen(route = "BluetoothPermissionScreen")

    @Serializable
    data object HomeScreen : Screen(route = "HomeScreen")

    @Serializable
    data object ChatScreen : Screen(route = "ChatScreen")
}