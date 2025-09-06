package com.developersphere.bechat.persentation.home_screen.widget

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.developersphere.bechat.R
import com.developersphere.bechat.ui.theme.BeChatTheme

@SuppressLint("MissingPermission")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenTopAppBar(isDiscovering: Boolean, scanDevice: () -> Unit, enableServer: () -> Unit) {
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
                    painter = painterResource(R.drawable.server),
                    contentDescription = "",
                    Modifier
                        .size(22.dp)
                        .clickable {
                            enableServer()
                        },
                    tint = MaterialTheme.colorScheme.onSurface,
                )
                Spacer(Modifier.width(12.dp))

                Text(
                    if (isDiscovering) "Stop" else "Scan",
                    style = TextStyle(
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.onSurface,
                        fontWeight = FontWeight.SemiBold,
                        textAlign = TextAlign.Center
                    ),
                    modifier = Modifier
                        .width(40.dp)
                        .align(Alignment.CenterVertically)
                        .clickable {
                            scanDevice()
                        },
                )

                Spacer(Modifier.width(12.dp))
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = "server",
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

@Composable
@Preview
fun HomeScreenTopAppBarPreview() {
    BeChatTheme {
        HomeScreenTopAppBar(true, {}, {})
    }
}

@Composable
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
fun HomeScreenTopAppBarDarkPreview() {
    BeChatTheme {
        HomeScreenTopAppBar(false, {}, {})
    }
}

