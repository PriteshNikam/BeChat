package com.developersphere.bechat.persentation.shared

import android.Manifest
import android.bluetooth.BluetoothDevice
import android.util.Log
import androidx.annotation.RequiresPermission
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.developersphere.bechat.data.bluetooth.BluetoothControllerImpl
import com.developersphere.bechat.domain.bluetooth.ConnectionResult
import com.developersphere.bechat.persentation.home_screen.HomeScreenUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

//TODO
// check is location necessary for discovering the device.
// implement interface for bluetooth helper class.
// add scan button on top app bar.

@HiltViewModel
class SharedViewModel @Inject constructor(
    private val bluetoothControllerImpl: BluetoothControllerImpl,
) : ViewModel() {

    private val _bluetoothUiState = MutableStateFlow(HomeScreenUiState())
    val bluetoothUiState: StateFlow<HomeScreenUiState> = _bluetoothUiState

    private var deviceConnectJob: Job? = null

    init {
        bluetoothControllerImpl.isConnected.onEach { isConnected ->
            _bluetoothUiState.update { state ->
                state.copy(isConnected = isConnected)
            }
        }.launchIn(viewModelScope)

        bluetoothControllerImpl.error.onEach { error ->
            _bluetoothUiState.update { state ->
                state.copy(errorMessage = error)
            }
        }.launchIn(viewModelScope)

        // collected available devices.
        viewModelScope.launch {
            bluetoothControllerImpl.scannedDevices.collect { active ->
                _bluetoothUiState.update { state ->
                    state.copy(availableDevices = active)
                }
            }
        }

        // collected paired devices
        viewModelScope.launch {
            _bluetoothUiState.update { state ->
                state.copy(
                    pairedDevices = bluetoothControllerImpl.getPairedDevices()
                )
            }
        }

        // collected bluetooth status
        viewModelScope.launch {
            bluetoothControllerImpl.isBluetoothActive.collect { active ->
                _bluetoothUiState.update { state ->
                    state.copy(isBluetoothEnable = active)
                }
            }
        }

        // collected bluetooth discovering status
        viewModelScope.launch {
            bluetoothControllerImpl.isDiscovering.collect { active ->
                _bluetoothUiState.update { state ->
                    state.copy(isDiscovering = active)
                }
            }
        }
    }

    @RequiresPermission(Manifest.permission.BLUETOOTH_SCAN)
    fun startDiscovering() {
        viewModelScope.launch {
            Log.d("Bechat", "Ra1 VM startDiscovering")
            bluetoothControllerImpl.startDiscovering()
        }
    }

    fun stopDiscovering() {
        viewModelScope.launch {
            Log.d("Bechat", "Ra1 VM stopDiscovering")
            bluetoothControllerImpl.stopDiscovering()
        }
    }

    fun connectDevice(device: BluetoothDevice) {
        viewModelScope.launch {
            _bluetoothUiState.update { state ->
                state.copy(isConnecting = true)
            }
            deviceConnectJob = bluetoothControllerImpl.connectDevice(device).listen()
        }
    }

    fun disConnectDevice() {
        viewModelScope.launch {
            deviceConnectJob?.cancel()
            bluetoothControllerImpl.closeConnection()
            _bluetoothUiState.update { state ->
                state.copy(
                    isConnected = false,
                    isConnecting = false,
                )
            }
        }
    }

    fun sendMessage(message: String) {
        Log.d("Bechat","Ra1 VM msg -> $message")
        viewModelScope.launch {
            var bluetoothMessage = bluetoothControllerImpl.sendMessage(message)
            Log.d("Bechat","Ra1 VM return msg -> $bluetoothMessage")
            bluetoothMessage?.let {
                _bluetoothUiState.update { state ->
                    state.copy(
                        chatMessages = state.chatMessages + it
                    )
                }
            }
        }
    }

    fun waitingForIncomingConnection() {
        viewModelScope.launch {
            _bluetoothUiState.update { state ->
                state.copy(
                    isConnecting = true,
                )
            }
            deviceConnectJob = bluetoothControllerImpl.startServer().listen()
        }
    }

    fun Flow<ConnectionResult>.listen(): Job {
        return onEach { result ->
            when (result) {

                is ConnectionResult.ConnectionEstablished -> {
                    _bluetoothUiState.update { state ->
                        state.copy(
                            isConnected = true,
                            isConnecting = false,
                            errorMessage = null,
                        )
                    }
                }

                is ConnectionResult.Error -> {
                    _bluetoothUiState.update { state ->
                        state.copy(
                            isConnected = false,
                            isConnecting = false,
                            errorMessage = result.errorMessage
                        )
                    }
                }

                is ConnectionResult.ConnectionLost -> {
                    // todo:: connection list state.
                }

                is ConnectionResult.DataTransferredSuccessFully -> {
                    _bluetoothUiState.update { state ->
                        state.copy(
                            chatMessages = state.chatMessages + result.message,
                        )
                    }
                }
            }
        }.catch { throwable ->
            bluetoothControllerImpl.closeConnection()
            _bluetoothUiState.update { state ->
                state.copy(
                    isConnected = false,
                    isConnecting = false,
                )
            }
        }.launchIn(viewModelScope)
    }

    override fun onCleared() {
        super.onCleared()
        bluetoothControllerImpl.release()
    }
}