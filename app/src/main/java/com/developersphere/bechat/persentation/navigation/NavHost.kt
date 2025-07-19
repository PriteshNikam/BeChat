package com.developersphere.bechat.persentation.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.developersphere.bechat.persentation.bluetooth_permission_screen.BluetoothPermissionScreen
import com.developersphere.bechat.persentation.chat_screen.ChatScreen
import com.developersphere.bechat.persentation.home_screen.HomeScreen
import kotlinx.serialization.Serializable

@Serializable
sealed class Screen(val route: String) {
    @Serializable
    data object BluetoothPermissionScreen : Screen("BluetoothPermissionScreen")

    @Serializable
    data object HomeScreen : Screen("HomeScreen")

    @Serializable
    data object ChatScreen : Screen("ChatScreen")
}

@Composable
fun AppNavHost(navHostController: NavHostController = rememberNavController()) {
    NavHost(
        navController = navHostController,
        startDestination = Screen.HomeScreen
    ) {

        composable<Screen.HomeScreen> {
            HomeScreen { screen ->
                navHostController.navigate(screen)
            }
        }

        composable<Screen.BluetoothPermissionScreen> {
            BluetoothPermissionScreen { screen ->
                Log.d("BeChat", "beScreen $screen")
                navHostController.navigate(screen)
            }
        }

        composable<Screen.ChatScreen> {
            ChatScreen { screen ->
                if (screen == null) {
                    Log.d("BeChat", "chatScreen $screen")
                    navHostController.popBackStack()
                } else {
                    Log.d("BeChat", "chatScreen $screen")
                    navHostController.navigate(screen)
                }

            }
        }
    }
}
