package com.developersphere.bechat.persentation.home_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.developersphere.bechat.persentation.navigation.Screen
import com.developersphere.bechat.ui.theme.BeChatTheme

@Composable
fun HomeScreen(navigate: (Screen) -> Unit) {
    Scaffold(
        topBar = {
            HomeScreenTopAppBar()
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues = padding)
        ) {
            Column {
                PairedDevices(navigate)
                AvailableDevices()
            }
        }
    }
}

@Composable
fun PairedDevices(navigate: (Screen) -> Unit) {
    Box(Modifier.padding(16.dp)) {
        Column {
            Text(
                "Paired Devices",
                style = TextStyle(
                    fontSize = 20.sp,
                )
            )
            Spacer(modifier = Modifier.height(16.dp))
            LazyColumn {
                items(2) {
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        color = MaterialTheme.colorScheme.primaryContainer,
                        shape = RoundedCornerShape(12.dp),
                        onClick = { navigate.invoke(Screen.ChatScreen) }
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier
                                .padding(24.dp)
                                .background(color = Color.Transparent),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.AccountCircle,
                                    contentDescription = "profile",
                                    tint = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.size(64.dp)
                                )
                                Spacer(modifier = Modifier.width(24.dp))
                                Column {
                                    Text( // TODO :: create separate color scheme for text.
                                        "Device $it",
                                        style = TextStyle(
                                            fontSize = 20.sp,
                                            color = MaterialTheme.colorScheme.onBackground,
                                            fontWeight = FontWeight.Bold
                                        )
                                    )
                                    Text(
                                        "Paired",
                                        style = TextStyle(
                                            fontSize = 16.sp,
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
            }
        }
    }
}

@Composable
fun AvailableDevices() {
    Box(Modifier.padding(16.dp)) {
        Column {
            Text(
                "Available Devices",
                style = TextStyle(
                    fontSize = 20.sp,
                )
            )
            Spacer(modifier = Modifier.height(16.dp))
            LazyColumn {
                items(2) {
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        color = MaterialTheme.colorScheme.primaryContainer,
                        shape = RoundedCornerShape(12.dp),
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier
                                .padding(24.dp)
                                .background(color = Color.Transparent),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.AccountCircle,
                                    contentDescription = "profile",
                                    tint = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.size(64.dp)
                                )
                                Spacer(modifier = Modifier.width(24.dp))
                                Column {
                                    Text( // TODO :: create separate color scheme for text.
                                        "Device $it",
                                        style = TextStyle(
                                            fontSize = 20.sp,
                                            color = MaterialTheme.colorScheme.onBackground,
                                            fontWeight = FontWeight.Bold
                                        )
                                    )
                                    Text(
                                        "Paired",
                                        style = TextStyle(
                                            fontSize = 16.sp,
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
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenTopAppBar() {
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
        navigationIcon = {
            IconButton(onClick = {}) {
                Icon(
                    imageVector = Icons.AutoMirrored.Default.ArrowBack,
                    contentDescription = "",
                    tint = MaterialTheme.colorScheme.onSurface,
                )
            }
        },
        actions = {
            IconButton(
                onClick = {}
            ) {
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = "",
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
        HomeScreen({})
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    BeChatTheme {
        HomeScreen({})
    }
}