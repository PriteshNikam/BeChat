package com.developersphere.bechat.persentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController

@Composable
fun AppNavHost(
    navHostController: NavHostController = rememberNavController(),
    appViewModel: AppViewModel = hiltViewModel(),
) {
    val startingDestination = appViewModel.getStartingDestination.collectAsStateWithLifecycle()

    NavHost(
        navController = navHostController,
        startDestination = startingDestination.value,
    ) {
        permissionGraph(navHostController)
        mainGraph(navHostController)
    }
}

@Composable
inline fun <reified T : ViewModel> NavBackStackEntry.sharedViewModel(
    navHostController: NavHostController,
    graph: Graph,
    backStackEntry: NavBackStackEntry,
): T {
    val parentEntry = remember(backStackEntry) {
        navHostController.getBackStackEntry<Graph>(graph)
    }
    return hiltViewModel(parentEntry)
}