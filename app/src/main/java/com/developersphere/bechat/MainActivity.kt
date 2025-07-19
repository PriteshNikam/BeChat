package com.developersphere.bechat

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.developersphere.bechat.persentation.navigation.AppNavHost
import com.developersphere.bechat.ui.theme.BeChatTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BeChatTheme {
                AppNavHost()
            }
        }
    }
}