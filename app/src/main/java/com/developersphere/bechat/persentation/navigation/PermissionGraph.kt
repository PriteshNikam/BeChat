package com.developersphere.bechat.persentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.developersphere.bechat.persentation.bluetooth_permission_screen.BluetoothPermissionScreen

fun NavGraphBuilder.permissionGraph(navHostController: NavHostController) {
    navigation<Graph.Permission>(
        startDestination = Screen.BluetoothPermissionScreen
    ) {
        composable<Screen.BluetoothPermissionScreen> {
            BluetoothPermissionScreen(
                navigate = { screen ->
                    navHostController.navigate(Graph.Main) {
                        popUpTo(Screen.BluetoothPermissionScreen) {
                            inclusive = true
                        }
                    }
                }
            )
        }
    }
}