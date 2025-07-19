package com.developersphere.bechat.persentation.bluetooth_permission_screen

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.developersphere.bechat.R
import com.developersphere.bechat.persentation.navigation.Screen

@Composable
fun BluetoothPermissionScreen(navigate: (Screen) -> Unit) {
    Column(modifier = Modifier.fillMaxSize()) {
        Icon(
            painter = painterResource(R.drawable.ic_launcher_background),
            modifier = Modifier.size(50.dp),
            tint = MaterialTheme.colorScheme.primary,
            contentDescription = ""
        )
        Text("Permission Screen", fontWeight = FontWeight.Bold)

        Button(
            onClick = {
                Log.d("BeChat","chatScreen $navigate")
                navigate.invoke(Screen.HomeScreen)
            }) {
            Text("next")
        }
    }
}