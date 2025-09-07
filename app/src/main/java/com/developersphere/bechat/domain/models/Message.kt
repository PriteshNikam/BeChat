package com.developersphere.bechat.domain.models

data class Message(
    val message: String? = null,
    val msgId: Int,
    val senderName: String? = null,
    val isSentByUser: Boolean = true,
    val status: MessageStatus = MessageStatus.SENT
)

enum class MessageStatus{
    SENT,
    FAILED,
}