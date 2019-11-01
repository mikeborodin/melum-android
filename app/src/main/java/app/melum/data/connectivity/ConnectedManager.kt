package app.melum.data.connectivity

import androidx.lifecycle.LiveData

interface ConnectedManager {

    val isConnected: LiveData<Boolean>
    val currentValue: Boolean

    fun startListening()
    fun stopListening()
}
