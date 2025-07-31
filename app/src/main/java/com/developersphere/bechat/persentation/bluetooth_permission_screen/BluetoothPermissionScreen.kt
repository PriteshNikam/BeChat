package com.developersphere.bechat.persentation.bluetooth_permission_screen

import android.content.res.Configuration
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.developersphere.bechat.R
import com.developersphere.bechat.persentation.navigation.Screen
import com.developersphere.bechat.ui.theme.BeChatTheme

@Composable
fun BluetoothPermissionScreen(navigate: (Screen) -> Unit) {
    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .fillMaxSize(),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                modifier = Modifier.size(
                    250.dp
                ),
                painter = painterResource(R.drawable.bluetooth_logo),
                contentDescription = ""
            )
            Text(
                "Bluetooth is Off", fontSize = 24.sp,
                color = MaterialTheme.colorScheme.onBackground,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                "To start chatting please enable bluetooth.",
                fontSize = 24.sp,
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.Center
            )
        }

        Button(
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary,
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            shape = RoundedCornerShape(16.dp),
            elevation = ButtonDefaults.buttonElevation(defaultElevation = 0.dp),
            onClick = {
                navigate.invoke(Screen.HomeScreen)
            }) {
            Log.d("BeChat", "Primary Color: ${MaterialTheme.colorScheme.primary}")
            Text(
                modifier = Modifier.padding(8.dp),
                text = "Enable Bluetooth",
                fontSize = 24.sp,
            )
        }
    }
}

@Preview
@Composable
fun BluetoothPermissionScreenPreview() {
    BeChatTheme {
        BluetoothPermissionScreen({})
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun BluetoothPermissionScreenDarkPreview() {
    BeChatTheme(darkTheme = true) {
        BluetoothPermissionScreen({})
    }
}