package com.hfad.services

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager

class BroadReceiver : BroadcastReceiver() {

    private val simple:Simple = Simple()

    @SuppressLint("UnsafeProtectedBroadcastReceiver")
    override fun onReceive(context: Context, intent: Intent) {
        connect?.onNetwork(isConnectedChanged(context))
        simple.list()
    }

    private fun isConnectedChanged(context: Context): Boolean {
        val connMgr = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connMgr.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnectedOrConnecting
    }

    companion object {
        var connect: ConnectionNetworkListener? = null
    }

    interface ConnectionNetworkListener {
        fun onNetwork(isConnected: Boolean)
    }
}