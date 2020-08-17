package com.hellofresh.chiragtest.network

import android.content.Context
import android.net.ConnectivityManager

class NetworkMonitor(private val context:Context) {

    private fun isNetworkConnected(): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        connectivityManager.let {
            val activeNetwork = connectivityManager.activeNetworkInfo
            activeNetwork?.let {
                return activeNetwork.isConnected
            }
        }
        return false
    }

}