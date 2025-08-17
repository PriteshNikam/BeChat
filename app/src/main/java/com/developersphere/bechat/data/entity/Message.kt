package com.developersphere.bechat.data.entity

data class Message(val message: String, val msgId: Int, val isSentByUser: Boolean = true)