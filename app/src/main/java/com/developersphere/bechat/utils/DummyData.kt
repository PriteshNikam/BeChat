package com.developersphere.bechat.utils

import androidx.compose.runtime.mutableStateListOf
import com.developersphere.bechat.data.entity.Message

object DummyData {
    val dummyMessages = mutableStateListOf(
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
}