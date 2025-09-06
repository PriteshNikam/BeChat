package com.developersphere.bechat.persentation.bluetooth_permission_screen

import android.Manifest
import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import androidx.annotation.RequiresPermission
import androidx.lifecycle.ViewModel
import com.developersphere.bechat.data.bluetooth.BluetoothControllerImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class BluetoothPermissionViewModel @Inject constructor(val bluetoothControllerImpl: BluetoothControllerImpl) :
    ViewModel() {

    val isBluetoothEnabled: StateFlow<Boolean> = bluetoothControllerImpl.isBluetoothActive

    private val _navigateToHome = MutableStateFlow(false)
    val navigateToHome: StateFlow<Boolean> = _navigateToHome.asStateFlow()

    @RequiresPermission(Manifest.permission.BLUETOOTH_CONNECT)
    fun enableBluetooth(launcher: ActivityResultLauncher<Intent>) {
        bluetoothControllerImpl.enableBluetooth(launcher) {
            _navigateToHome.value = true
        }
    }

    fun resetNavigationFlag() {
        _navigateToHome.value = false
    }

    fun hasPermission(permission:String):Boolean{
        return bluetoothControllerImpl.hasPermission(permission)
    }
}