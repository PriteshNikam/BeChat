package com.developersphere.bechat.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.developersphere.bechat.persentation.bluetooth_permission_screen.BluetoothPermissionScreen
import kotlinx.serialization.Serializable


sealed class Screen {
    @Serializable
    data object BluetoothPermissionScreen: Screen()
    data object HomeScreen: Screen()
    data object ChatScreen: Screen()
}

@Composable
fun AppNavHost(navHostController: NavHostController) {
    NavHost(navController = navHostController, startDestination = Screen.BluetoothPermissionScreen){
        composable<Screen.BluetoothPermissionScreen> {
            BluetoothPermissionScreen()
        }
    }
}