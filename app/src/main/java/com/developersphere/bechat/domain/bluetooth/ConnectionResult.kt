package com.developersphere.bechat.domain.bluetooth

import com.developersphere.bechat.domain.models.Message

sealed interface ConnectionResult {
    object ConnectionEstablished : ConnectionResult
    data class Error(val errorMessage: String) : ConnectionResult
    data class ConnectionLost(val errorMessage: String) : ConnectionResult
    data class DataTransferredSuccessFully(val message: Message, val isSentByUser: Boolean) : ConnectionResult
}
