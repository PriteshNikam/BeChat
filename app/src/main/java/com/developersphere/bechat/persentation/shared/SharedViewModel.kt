package com.developersphere.bechat.persentation.shared

import android.util.Log
import androidx.lifecycle.ViewModel
import com.developersphere.bechat.utils.BluetoothHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(bluetoothHelper: BluetoothHelper) : ViewModel() {
    private val isBluetoothEnable = bluetoothHelper.isBluetoothActive

    fun getBluetoothList(){
        Log.d("BeChat","Ra1 ====>>>> $isBluetoothEnable")
    }
}