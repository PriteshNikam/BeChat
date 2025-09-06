package com.developersphere.bechat.data.service

import android.bluetooth.BluetoothSocket
import android.util.Log
import com.developersphere.bechat.data.toMessage
import com.developersphere.bechat.domain.bluetooth.ConnectionResult
import com.developersphere.bechat.domain.bluetooth.DataTransferException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext

class BluetoothDataService(val bluetoothSocket: BluetoothSocket) {
    fun listenForMessages(): Flow<ConnectionResult> {
        return flow {
            if (!bluetoothSocket.isConnected) {
                return@flow
            }
            val buffer = ByteArray(1024)
            while (true) {
                val byteCount = try {
                    bluetoothSocket.inputStream.read(buffer)
                } catch (exception: Exception) {
                    throw DataTransferException()
                }

                emit(
                    ConnectionResult.DataTransferredSuccessFully(
                        message = buffer.decodeToString(endIndex = byteCount).toMessage(),
                        isSentByUser = true
                    )
                )
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun sendMessage(byteArray: ByteArray): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                bluetoothSocket.outputStream.write(byteArray)
                Log.d("BLE", "Ra1 service Send success")
            } catch (e: Exception) {
                Log.e("BLE", "Ra1 Send failed: ${e.printStackTrace()}")
                e.printStackTrace()
                return@withContext false
            }
            true
        }
    }
}