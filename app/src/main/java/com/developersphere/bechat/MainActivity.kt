package com.developersphere.bechat

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.developersphere.bechat.persentation.navigation.AppNavHost
import com.developersphere.bechat.ui.theme.BeChatTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        // Prevents the system from resizing or pushing the layout when the keyboard appears.
        // This ensures that the top app bar remains visible and avoids layout flicker
        // especially in chat screens with input fields at the bottom.
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING)

        setContent {
            BeChatTheme {
                AppNavHost()
            }
        }
    }
}