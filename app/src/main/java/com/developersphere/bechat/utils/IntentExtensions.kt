package com.developersphere.bechat.utils

import android.content.Intent
import android.os.Build
import android.os.Parcelable

// make this common for all broadcast receiver.
@Suppress("DEPRECATION")
fun <T : Parcelable> Intent.getParcelableCompat(key: String, clazz: Class<T>): T? {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        getParcelableExtra(key, clazz)
    } else {
        val value = getParcelableExtra(key) as? T
        if (clazz.isInstance(value)) {
            @Suppress("UNCHECKED_CAST")
            value as T
        } else {
            null
        }
    }
}