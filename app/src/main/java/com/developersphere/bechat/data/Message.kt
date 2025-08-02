package com.developersphere.bechat.data

data class Message(val message: String, val msgId: Int, val isSentByUser: Boolean = true)
