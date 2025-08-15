package com.developersphere.bechat.persentation.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class Graph(val route: String) {
    @Serializable
    data object Permission : Graph(route = "Permission")

    @Serializable
    data object Main : Graph(route = "Main")
}
