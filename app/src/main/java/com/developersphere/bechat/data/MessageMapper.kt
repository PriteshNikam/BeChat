package com.developersphere.bechat.data

import com.developersphere.bechat.domain.models.Message
import kotlin.random.Random

fun String.toMessage(): Message {
    val messageParts = this.split('#')
    return Message(
        senderName = messageParts[0],
        message = messageParts[1],
        isSentByUser = false,
        msgId = Random.nextInt()
    )
}

// prefer json serialisation here
fun Message.toByteArray(): ByteArray {
    return "$senderName#$message".toByteArray()
}