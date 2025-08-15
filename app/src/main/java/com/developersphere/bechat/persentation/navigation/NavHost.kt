package com.developersphere.bechat.persentation.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
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
    data object BluetoothPermissionScreen : Screen(route =  "BluetoothPermissionScreen")

    @Serializable
    data object HomeScreen : Screen(route =  "HomeScreen")

    @Serializable
    data object ChatScreen : Screen(route = "ChatScreen")
}

@Composable
fun AppNavHost(
    navHostController: NavHostController = rememberNavController(),
    appViewModel: AppViewModel = hiltViewModel(),
) {

    val startingDestination = appViewModel.getStartingDestination.collectAsStateWithLifecycle()

    NavHost(
        navController = navHostController,
        startDestination = startingDestination.value
    ) {

        composable<Screen.HomeScreen> {
            HomeScreen { screen ->
                navHostController.navigate(screen)
            }
        }

        composable<Screen.BluetoothPermissionScreen> {
            BluetoothPermissionScreen(
                navigate = { screen ->
                    navHostController.navigate(screen) {
                        popUpTo(Screen.BluetoothPermissionScreen) {
                            inclusive = true
                        }
                    }
                }
            )
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
