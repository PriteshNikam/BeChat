package com.developersphere.bechat.persentation.home_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.compose.rememberNavController
import com.developersphere.bechat.persentation.navigation.AppNavHost
import com.developersphere.bechat.persentation.navigation.Screen

@Composable
fun HomeScreen(navigate: (Screen) -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("HomeScreen", fontWeight = FontWeight.Bold)
        Button(onClick = {
            navigate.invoke(Screen.ChatScreen)
        }) {
            Text("next")
        }
    }
}