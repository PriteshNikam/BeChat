package com.developersphere.bechat.persentation.chat_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.developersphere.bechat.persentation.navigation.Screen

@Composable
fun ChatScreen(navigation:(Screen?)-> Unit){
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("ChatScreen", fontWeight = FontWeight.Bold)
        Button(onClick = {
            navigation(null)
        }) {
            Text("next")
        }
    }
}