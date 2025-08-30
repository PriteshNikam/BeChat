package com.developersphere.bechat.persentation.home_screen.widget

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

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