package app.melum.data.connectivity

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.Network
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class AndroidConnectedManager(private val appContext: Context) :
    ConnectedManager {

    private val _isConnected: MutableLiveData<Boolean> = MutableLiveData()

    override val isConnected: LiveData<Boolean> = _isConnected

    override val currentValue: Boolean
        get() = _isConnected.value ?: false

    override fun startListening() {
        refresh()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            connectivityManager().registerDefaultNetworkCallback(callback)
        } else {
            appContext.registerReceiver(
                receiver,
                IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
            )
        }
    }

    override fun stopListening() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            connectivityManager().unregisterNetworkCallback(callback)
        } else {
            appContext.unregisterReceiver(receiver)
        }
    }

    private val callback by lazy {
        @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
        object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network?) {
                network?.let {
                    super.onAvailable(it)
                    _isConnected.postValue(true)
                }
            }

            override fun onLost(network: Network?) {
                network?.let {
                    super.onLost(it)
                    _isConnected.postValue(false)
                }
            }
        }
    }

    private val receiver by lazy {
        object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                if (intent?.action != ConnectivityManager.CONNECTIVITY_ACTION) return
                refresh()
            }
        }
    }

    private fun connectivityManager() =
        appContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    private fun refresh() = _isConnected.postValue(isConnected())

    private fun isConnected(): Boolean =
        connectivityManager().activeNetworkInfo?.isConnected == true

}