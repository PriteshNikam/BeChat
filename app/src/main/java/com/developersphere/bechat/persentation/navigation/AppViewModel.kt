package com.developersphere.bechat.persentation.navigation

import androidx.lifecycle.ViewModel
import com.developersphere.bechat.utils.BluetoothHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class AppViewModel @Inject constructor(bluetoothHelper: BluetoothHelper) : ViewModel() {

    private val startingDestination = MutableStateFlow<Graph>(Graph.Permission)
    var getStartingDestination: StateFlow<Graph> = startingDestination

    init {
        startingDestination.value =
            if (bluetoothHelper.isBluetoothActive.value) Graph.Main else Graph.Permission
    }
}