package com.developersphere.bechat.persentation.home_screen

import android.Manifest
import android.bluetooth.BluetoothDevice
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.os.Build
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.developersphere.bechat.persentation.home_screen.widget.HomeScreenTopAppBar
import com.developersphere.bechat.persentation.home_screen.widget.ListItem
import com.developersphere.bechat.persentation.home_screen.widget.NoDeviceFound
import com.developersphere.bechat.persentation.navigation.Screen
import com.developersphere.bechat.persentation.shared.SharedViewModel

@Composable
fun HomeScreen(
    navigate: (Screen) -> Unit,
    sharedViewModel: SharedViewModel,
) {
    val homeScreenUiState = sharedViewModel.bluetoothUiState.collectAsState()
    val context = LocalContext.current

    // launch effect true/Unit means it is only triggered once.
    LaunchedEffect(Unit) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            if (ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.BLUETOOTH_CONNECT
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                if (homeScreenUiState.value.isBluetoothEnable) {
                    sharedViewModel.startDiscovering()
                }
            }
        }
    }

    Scaffold(
        topBar = {
            HomeScreenTopAppBar(
                isDiscovering = homeScreenUiState.value.isDiscovering,
                scanDevice = {
                    if (homeScreenUiState.value.isBluetoothEnable) {
                        if (homeScreenUiState.value.isDiscovering) {
                            sharedViewModel.stopDiscovering()
                        } else {
                            sharedViewModel.startDiscovering()
                        }
                    }
                },
                enableServer = {
                    sharedViewModel.waitingForIncomingConnection()
                }

            )
        }
    ) { padding ->
        when {
            homeScreenUiState.value.isConnecting -> {
                Box(Modifier.fillMaxSize()) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
            }

            homeScreenUiState.value.isConnected -> {
                Toast.makeText(context, "Connected", Toast.LENGTH_SHORT).show()
                navigate(Screen.ChatScreen)
            }

            else -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues = padding)
                ) {
                    LazyColumn(
                        modifier = Modifier.padding(horizontal = 16.dp),
                    ) {
                        item {
                            Text(
                                modifier = Modifier.padding(vertical = 8.dp),
                                text = "Paired Devices",
                                style = TextStyle(
                                    fontSize = 20.sp,
                                )
                            )
                        }
                        if (homeScreenUiState.value.pairedDevices?.isNotEmpty() == true) {
                            items(homeScreenUiState.value.pairedDevices!!) { device ->
                                ListItem(device, connectDevice = {
                                    if (device.bondState != BluetoothDevice.BOND_BONDED || !homeScreenUiState.value.isConnected) {
                                        sharedViewModel.connectDevice(it)
                                    }

                                    if (homeScreenUiState.value.isConnected) {
                                        navigate(Screen.ChatScreen)
                                    }
                                })
                            }
                        } else {
                            item {
                                NoDeviceFound()
                            }
                        }

                        item {
                            Text(
                                modifier = Modifier.padding(vertical = 8.dp),
                                text = "Available Devices",
                                style = TextStyle(
                                    fontSize = 20.sp,
                                )
                            )
                        }

                        if (homeScreenUiState.value.availableDevices?.isNotEmpty() == true) {
                            items(homeScreenUiState.value.availableDevices!!) { device ->
                                ListItem(
                                    device,
                                    connectDevice = {
                                        sharedViewModel.connectDevice(device)
                                    }
                                )
                            }
                        } else {
                            item {
                                NoDeviceFound()
                            }
                            if (homeScreenUiState.value.isDiscovering) {
                                item {
                                    Text("Scanning...")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun HomeScreenPreviewDark() {
    HomeScreen(
        {},
        sharedViewModel = hiltViewModel()
    )
}

@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreen(
        {},
        sharedViewModel = hiltViewModel()
    )

}