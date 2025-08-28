package com.developersphere.bechat.persentation.shared

import android.Manifest
import androidx.annotation.RequiresPermission
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.developersphere.bechat.persentation.home_screen.HomeScreenUiState
import com.developersphere.bechat.utils.BluetoothHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

//TODO
// check is location necessary for discovering the device.
// implement interface for bluetooth helper class.
// add scan button on top app bar.

@HiltViewModel
class SharedViewModel @Inject constructor(
    private val bluetoothHelper: BluetoothHelper,
) : ViewModel() {

    private val _bluetoothUiState = MutableStateFlow(HomeScreenUiState())
    val bluetoothUiState: StateFlow<HomeScreenUiState> = _bluetoothUiState

    init {
        viewModelScope.launch {
            bluetoothHelper.scannedDevices.collect { active ->
                _bluetoothUiState.update { state ->
                    state.copy(availableDevices = active)
                }
            }
        }

        viewModelScope.launch {
            _bluetoothUiState.update { state ->
                state.copy(
                    pairedDevices = bluetoothHelper.getPairedDevices()
                )
            }
        }

        viewModelScope.launch {
            bluetoothHelper.isBluetoothActive.collect { active ->
                _bluetoothUiState.update { state ->
                    state.copy(isBluetoothEnable = active)
                }
            }
        }

        viewModelScope.launch {
            bluetoothHelper.isDiscovering.collect { active ->
                _bluetoothUiState.update { state ->
                    state.copy(isDiscovering = active)
                }
            }
        }
    }

    @RequiresPermission(Manifest.permission.BLUETOOTH_SCAN)
    fun startDiscovering() {
        bluetoothHelper.startDiscovery()
    }
}