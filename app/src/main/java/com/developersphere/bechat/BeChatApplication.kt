package com.developersphere.bechat

import android.app.Application
import androidx.compose.runtime.Composer
import androidx.compose.runtime.ExperimentalComposeRuntimeApi
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class BeChatApplication : Application() {
    @OptIn(ExperimentalComposeRuntimeApi::class)
    override fun onCreate() {
        super.onCreate()

        // for debugging compose crash, remove for production.
        Composer.setDiagnosticStackTraceEnabled(true)
    }
}