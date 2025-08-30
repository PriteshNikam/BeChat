package com.developersphere.bechat.domain.bluetooth

sealed interface ConnectionResult {
    object ConnectionEstablished : ConnectionResult
    data class Error(val errorMessage: String) : ConnectionResult
}
