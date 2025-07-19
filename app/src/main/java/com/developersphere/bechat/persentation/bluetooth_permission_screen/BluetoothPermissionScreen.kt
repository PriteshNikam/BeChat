package com.developersphere.bechat.persentation.bluetooth_permission_screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.developersphere.bechat.R

@Composable
fun BluetoothPermissionScreen() {
    Column(modifier = Modifier.fillMaxSize()) {
        Icon(
            painter = painterResource(R.drawable.ic_launcher_background),
            modifier = Modifier.size(50.dp),
            tint = MaterialTheme.colorScheme.primary,
            contentDescription = ""
        )
    }
}