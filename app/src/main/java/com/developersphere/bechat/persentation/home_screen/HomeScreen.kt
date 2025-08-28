package com.developersphere.bechat.persentation.home_screen

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.developersphere.bechat.R
import com.developersphere.bechat.domain.enums.BondState
import com.developersphere.bechat.domain.models.Device
import com.developersphere.bechat.persentation.navigation.Screen
import com.developersphere.bechat.persentation.shared.SharedViewModel
import com.developersphere.bechat.ui.theme.BeChatTheme

@Composable
fun HomeScreen(
    navigate: (Screen) -> Unit,
    sharedViewModel: SharedViewModel,
) {
    val homeScreenUiState = sharedViewModel.bluetoothUiState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    LaunchedEffect(true) {
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
            HomeScreenTopAppBar(sharedViewModel)
        }
    ) { padding ->
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
                        ListItem(device, navigate)
                    }
                } else {
                    if (!homeScreenUiState.value.isDiscovering) {
                        item {
                            NoDeviceFound()
                        }
                    } else {
                        item {
                            Text("Scanning...")
                        }
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
                        ListItem(device, navigate)
                    }
                } else {
                    item {
                        NoDeviceFound()
                    }
                    item {
                        Button(onClick = { sharedViewModel.startDiscovering() }) {
                            Text("Scan")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun NoDeviceFound() {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clip(
                shape = RoundedCornerShape(size = 12.dp),
            ),
        color = MaterialTheme.colorScheme.primaryContainer
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(16.dp)

        ) {
            Text(
                "No devices found",
                style = TextStyle(
                    fontSize = 20.sp,
                ),
            )
        }
    }
}

@Composable
fun ListItem(device: Device, navigate: (Screen) -> Unit) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        color = MaterialTheme.colorScheme.primaryContainer,
        shape = RoundedCornerShape(12.dp),
        onClick = { navigate.invoke(Screen.ChatScreen) }
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .padding(6.dp)
                .background(color = Color.Transparent),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = "profile",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(44.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text( // TODO :: create separate color scheme for text.
                        device.name.toString(),
                        style = TextStyle(
                            fontSize = 16.sp,
                            color = MaterialTheme.colorScheme.onBackground,
                            fontWeight = FontWeight.Bold
                        )
                    )
                    Text(
                        device.bondState?.name ?: BondState.UNKNOWN.name,
                        style = TextStyle(
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    )
                }
            }
            Icon(
                imageVector = Icons.AutoMirrored.Default.KeyboardArrowRight,
                contentDescription = "arrow",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(34.dp),

                )
        }
    }
}

@SuppressLint("MissingPermission")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenTopAppBar(sharedViewModel: SharedViewModel) {
    TopAppBar(
        colors = TopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            scrolledContainerColor = MaterialTheme.colorScheme.onSurface,
            navigationIconContentColor = MaterialTheme.colorScheme.onSurface,
            titleContentColor = MaterialTheme.colorScheme.onSurface,
            actionIconContentColor = MaterialTheme.colorScheme.onSurface,
        ),
        title = {
            Text(
                "Selected Device",
                style = TextStyle(
                    color = MaterialTheme.colorScheme.onSurface,
                    fontSize = 24.sp
                )
            )
        },

        actions = {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.detect),
                    contentDescription = "",
                    Modifier
                        .size(22.dp)
                        .clickable {
                            sharedViewModel.startDiscovering()
                        },
                    tint = MaterialTheme.colorScheme.onSurface,
                )
                Spacer(Modifier.width(12.dp))
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = "",
                    Modifier
                        .size(22.dp)
                        .clickable {
                        },
                    tint = MaterialTheme.colorScheme.onSurface,
                )

            }
        }
    )
}


@Preview
@Composable
fun HomeScreenPreviewDark() {
    BeChatTheme(darkTheme = true) {
        HomeScreen(
            {},
            sharedViewModel = TODO()
        )
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    BeChatTheme {
        HomeScreen(
            {},
            sharedViewModel = TODO()
        )
    }
}