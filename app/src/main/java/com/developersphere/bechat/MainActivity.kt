package com.developersphere.bechat

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.developersphere.bechat.ui.theme.BeChatTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BeChatTheme{
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Scaffold(Modifier.fillMaxSize()){ contentPadding ->
        Text(
            text = "Hello $name!",
            modifier = modifier.padding(contentPadding)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    BeChatTheme{
        Greeting("Android")
    }
}

@Preview
@Composable
fun GreetingDarkPreview(){
    BeChatTheme(darkTheme = true){
        Greeting("Android")
    }
}