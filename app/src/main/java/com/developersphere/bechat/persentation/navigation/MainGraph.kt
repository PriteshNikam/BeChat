package com.developersphere.bechat.persentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.developersphere.bechat.persentation.about_screen.AboutScreen
import com.developersphere.bechat.persentation.chat_screen.ChatScreen
import com.developersphere.bechat.persentation.connection_guide_screen.ConnectionGuideScreen
import com.developersphere.bechat.persentation.home_screen.HomeScreen
import com.developersphere.bechat.persentation.shared.SharedViewModel

fun NavGraphBuilder.mainGraph(navHostController: NavHostController) {
    navigation<Graph.Main>(
        startDestination = Screen.HomeScreen,
    ) {
        composable<Screen.HomeScreen> { backStack ->
            RegisterHomeScreen(navHostController, backStack)
        }

        composable<Screen.ChatScreen> { backStack ->
            RegisterChatScreen(navHostController, backStack)
        }

        composable<Screen.ConnectionGuideScreen> {
            RegisterConnectionGuideScreen(navHostController)
        }

        composable<Screen.AboutScreen> {
            RegisterAboutScreen(navHostController)
        }
    }
}

@Composable
fun RegisterHomeScreen(
    navHostController: NavHostController,
    backStack: NavBackStackEntry,
) {
    val viewModel = backStack.sharedViewModel<SharedViewModel>(
        navHostController = navHostController,
        graph = Graph.Main,
        backStackEntry = backStack,
    )

    HomeScreen(
        navigate = { screen ->
            navHostController.navigate(screen)
        },
        sharedViewModel = viewModel,
    )
}

@Composable
fun RegisterChatScreen(
    navHostController: NavHostController,
    backStack: NavBackStackEntry,
) {
    val viewModel = backStack.sharedViewModel<SharedViewModel>(
        navHostController = navHostController,
        graph = Graph.Main,
        backStackEntry = backStack,
    )

    ChatScreen(
        navigation = { screen ->
            if (screen == null) {
                navHostController.popBackStack()
            } else {
                navHostController.navigate(screen)
            }
        },
        sharedViewModel = viewModel,
    )
}

@Composable
fun RegisterConnectionGuideScreen(navHostController: NavHostController) {
    ConnectionGuideScreen(
        navigation = {
            navHostController.popBackStack()
        }
    )
}

@Composable
fun RegisterAboutScreen(navHostController: NavHostController) {
    AboutScreen(
        navigation = {
            navHostController.popBackStack()
        }
    )
}