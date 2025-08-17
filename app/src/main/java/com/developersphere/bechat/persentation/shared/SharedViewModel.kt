package com.developersphere.bechat.persentation.shared

import android.Manifest
import androidx.annotation.RequiresPermission
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.developersphere.bechat.domain.models.Device
import com.developersphere.bechat.utils.BluetoothHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


//TODO
// convert this into a state.
// check is location necessary for discovering the device.
// implement interface for bluetooth helper class.
// add scan button on top app bar.

@HiltViewModel
class SharedViewModel @Inject constructor(
    private val bluetoothHelper: BluetoothHelper,
) :
    ViewModel() {
    private val _isBluetoothEnable = MutableStateFlow(bluetoothHelper.isBluetoothActive)
    var isBluetoothEnable = _isBluetoothEnable.value

    private var _pairedDevices = MutableStateFlow<List<Device>?>(emptyList())
    val pairedDevices: StateFlow<List<Device>?> = _pairedDevices

    private var _availableDevices = MutableStateFlow<List<Device>>(emptyList())
    val availableDevices: StateFlow<List<Device>?> = _availableDevices


    init{
        viewModelScope.launch {
            bluetoothHelper.scannedDevices.collect{
                _availableDevices.value = it
            }
        }
    }

    @RequiresPermission(Manifest.permission.BLUETOOTH_CONNECT)
    fun getPairedDevices() {
        viewModelScope.launch {
            _pairedDevices.value = bluetoothHelper.getPairedDevices()
        }
    }

    @RequiresPermission(Manifest.permission.BLUETOOTH_SCAN)
    fun startDiscovering() {
        bluetoothHelper.startDiscovery()
    }
}