package com.developersphere.bechat.persentation.bluetooth_permission_screen

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.content.Intent
import android.content.res.Configuration
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.developersphere.bechat.R
import com.developersphere.bechat.persentation.navigation.Screen
import com.developersphere.bechat.ui.theme.BeChatTheme

@SuppressLint("MissingPermission")
@Composable
fun BluetoothPermissionScreen(
    navigate: (Screen) -> Unit,
    bluetoothPermissionViewModel: BluetoothPermissionViewModel = hiltViewModel(),
) {
    val context = LocalContext.current

    val navigateToHome = bluetoothPermissionViewModel.navigateToHome.collectAsStateWithLifecycle()

    // Enable Bluetooth launcher
    val enableBluetoothLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            navigate(Screen.HomeScreen)
        }
    }

    // Permission request launcher
    // required for android 12+
    val bluetoothPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { perms ->
        val canEnableBluetooth = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            perms[Manifest.permission.BLUETOOTH_CONNECT] == true
        } else true

        if (canEnableBluetooth && !bluetoothPermissionViewModel.isBluetoothEnabled.value) {
            enableBluetoothLauncher.launch(
                Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            )
        }
    }

    LaunchedEffect(navigateToHome) {
        if (navigateToHome.value) {
            navigate.invoke(Screen.HomeScreen)
        }
        bluetoothPermissionViewModel.resetNavigationFlag()
    }

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
            elevation = ButtonDefaults.buttonElevation(defaultElevation = 2.dp),
            onClick = {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                    if (bluetoothPermissionViewModel.hasPermission(Manifest.permission.BLUETOOTH_CONNECT)) {
                        bluetoothPermissionViewModel.enableBluetooth(enableBluetoothLauncher)
                    } else {
                        bluetoothPermissionLauncher.launch(
                            arrayOf(
                                Manifest.permission.BLUETOOTH_CONNECT,
                                Manifest.permission.BLUETOOTH_SCAN,
                                Manifest.permission.ACCESS_FINE_LOCATION,

                            )
                        )
                    }
                } else {
                    bluetoothPermissionViewModel.enableBluetooth(enableBluetoothLauncher)
                }
            }) {
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