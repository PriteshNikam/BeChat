package com.developersphere.bechat.persentation.bluetooth_permission_screen

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.location.LocationManager
import android.os.Build
import android.provider.Settings
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.*
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.developersphere.bechat.R
import com.developersphere.bechat.persentation.navigation.Screen
import com.developersphere.bechat.ui.theme.BeChatTheme
import kotlinx.coroutines.delay

@SuppressLint("MissingPermission")
@Composable
fun BluetoothPermissionScreen(
    navigate: (Screen) -> Unit,
    bluetoothPermissionViewModel: BluetoothPermissionViewModel = hiltViewModel(),
) {
    val navigateToHome = bluetoothPermissionViewModel.navigateToHome.collectAsStateWithLifecycle()
    val context = LocalContext.current

    // Launcher to enable Bluetooth
    val enableBluetoothLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            navigate(Screen.HomeScreen)
        } else {
            Toast.makeText(context, "Bluetooth must be enabled!", Toast.LENGTH_SHORT).show()
        }
    }
    var isLocationEnabled by remember { mutableStateOf(isLocationEnabled(context)) }

    // the best approach to change button text will be replacing this with broadcast receiver,
    // but as this is to small app do this loop logic.
    LaunchedEffect(Unit) {
        while(true) {
            isLocationEnabled = isLocationEnabled(context)
            delay(1000)
        }
    }

    // Permissions launcher
    val bluetoothPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { perms ->
        if (perms.values.all { it }) {
            handleBluetoothSetup(
                context,
                bluetoothPermissionViewModel,
                enableBluetoothLauncher,
                navigate
            )
        } else {
            Toast.makeText(context, "Permissions required!", Toast.LENGTH_SHORT).show()
        }
    }

    val composition by rememberLottieComposition(
        spec = LottieCompositionSpec.RawRes(R.raw.bluetooth_lottie)
    )

    LaunchedEffect(navigateToHome.value) {
        if (navigateToHome.value) {
            navigate(Screen.HomeScreen)
            bluetoothPermissionViewModel.resetNavigationFlag()
        }
    }

    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .fillMaxSize(),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            LottieAnimation(
                composition = composition,
                modifier = Modifier.size(250.dp),
                iterations = LottieConstants.IterateForever,
            )
            Spacer(modifier = Modifier.height(44.dp))
            Text(
                "Bluetooth is Off",
                fontSize = 24.sp,
                color = MaterialTheme.colorScheme.onBackground,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                "To start chatting please enable Bluetooth.",
                fontSize = 24.sp,
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.Center
            )
        }

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            shape = RoundedCornerShape(16.dp),
            elevation = ButtonDefaults.buttonElevation(defaultElevation = 2.dp),
            onClick = {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                    bluetoothPermissionLauncher.launch(
                        arrayOf(
                            Manifest.permission.BLUETOOTH_CONNECT,
                            Manifest.permission.BLUETOOTH_SCAN
                        )
                    )
                } else {
                    bluetoothPermissionLauncher.launch(
                        arrayOf(Manifest.permission.ACCESS_FINE_LOCATION)
                    )
                }

                isLocationEnabled = isLocationEnabled(context)
            }
        ) {
            Text(
                modifier = Modifier.padding(8.dp),
                text = if (isLocationEnabled) {
                    "Enable Bluetooth"
                } else {
                    "Enable Location"
                },
                fontSize = 24.sp,
            )
        }
    }
}

private fun handleBluetoothSetup(
    context: Context,
    bluetoothPermissionViewModel: BluetoothPermissionViewModel,
    enableBluetoothLauncher: ActivityResultLauncher<Intent>,
    navigate: (Screen) -> Unit,
) {
    if (!isLocationEnabled(context)) {
        Toast.makeText(
            context,
            "Please enable Location for Bluetooth scanning",
            Toast.LENGTH_LONG
        ).show()
        context.startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
        return
    }

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        if (!bluetoothPermissionViewModel.isBluetoothEnabled.value) {
            enableBluetoothLauncher.launch(Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE))
        } else {
            navigate(Screen.HomeScreen)
        }
    } else {
        navigate(Screen.HomeScreen)
    }
}

fun isLocationEnabled(context: Context): Boolean {
    val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
            locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
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