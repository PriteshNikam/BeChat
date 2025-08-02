package com.developersphere.bechat.persentation.chat_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstrainedLayoutReference
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.developersphere.bechat.R
import com.developersphere.bechat.persentation.navigation.Screen
import com.developersphere.bechat.ui.theme.BeChatTheme

data class Message(val message: String, val msgId: Int, val isSentByUser: Boolean = true)

val messageList = mutableListOf(
    Message("last msg", 1, true),
    Message("hello", 2, false),
    Message("hello", 3, true),
    Message("hello", 4, false),
    Message("hello", 5, true),
    Message("hello", 1, true),
    Message("hello", 2, false),
    Message("hello", 3, true),
    Message("hello", 4, false),
    Message("hello", 5, true),
    Message("hello", 1, true),
    Message("hello", 2, false),
    Message("hello", 3, true),
    Message("hello", 4, false),
    Message("hello", 5, true),
    Message("hello", 1, true),
    Message("hello", 2, false),
    Message("hello", 3, true),
    Message("hello", 4, false),
    Message("first msg", 5, true),
)

@Composable
fun ChatScreen(navigation: (Screen?) -> Unit) {
    Scaffold(
        topBar = {
            ChatScreenTopAppBar()
        },
        modifier = Modifier
            .fillMaxSize()
    ) { innerPadding ->
        Messages(innerPadding)
    }
}

@Composable
fun Messages(paddingValues: PaddingValues) {

    var message by rememberSaveable { mutableStateOf("") }
    val listState = rememberLazyListState()

    ConstraintLayout(
        modifier = Modifier
            .padding(paddingValues)
            .fillMaxSize()
    ) {
        val (list, sendMessage) = createRefs()

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .constrainAs(list) {
                    top.linkTo(parent.top)
                    bottom.linkTo(sendMessage.top)
                    height = Dimension.fillToConstraints
                },
            state = listState,
            reverseLayout = true,
            contentPadding = PaddingValues(vertical = 8.dp, horizontal = 8.dp),

            ) {
            items(messageList.count()) {
                if (messageList[it].isSentByUser) {
                    SentMessage(messageList[it].message)
                } else {
                    ReceivedMessage(messageList[it].message)
                }
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
                .constrainAs(sendMessage) {
                    bottom.linkTo(parent.bottom)
                },
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            TextField(
                value = message,
                onValueChange = { message = it },
                modifier = Modifier.weight(1f),
            )
            Spacer(Modifier.width(8.dp))
            IconButton(
                onClick = {},
                modifier = Modifier.size(24.dp)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Default.Send,
                    contentDescription = "send button"
                )
            }
        }
    }
}

@Composable
fun SendMessage(sendMessage: ConstrainedLayoutReference) {

}

@Composable
fun SentMessage(message: String) {
    Row(
        horizontalArrangement = Arrangement.End,
        modifier = Modifier
            .fillMaxSize()
            .padding(end = 8.dp)
    ) {
        Surface(
            color = MaterialTheme.colorScheme.primaryContainer,
            shape = RoundedCornerShape(12.dp, 12.dp, 0.dp, 12.dp),
        ) {
            Text(message, modifier = Modifier.padding(12.dp))
        }
    }
}

@Composable
fun ReceivedMessage(message: String) {
    Row(
        horizontalArrangement = Arrangement.Start,
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 8.dp)
    ) {
        Surface(
            color = MaterialTheme.colorScheme.primary,
            shape = RoundedCornerShape(12.dp, 12.dp, 12.dp, 0.dp),
        ) {
            Text(message, modifier = Modifier.padding(12.dp))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreenTopAppBar() {
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
                "Device name",
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
                    painter = painterResource(id = R.drawable.bluetooth),
                    contentDescription = "",
                    tint = MaterialTheme.colorScheme.onSurface,
                )
            }
        }
    )
}

@Preview
@Composable
fun ChatScreenPreview() {
    BeChatTheme {
        ChatScreen({})
    }
}

@Preview
@Composable
fun ChatScreenDarkPreview() {
    BeChatTheme(darkTheme = true) {
        ChatScreen({})
    }
}