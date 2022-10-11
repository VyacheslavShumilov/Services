package com.hfad.services

import android.annotation.SuppressLint
import android.content.*
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import com.hfad.services.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), BroadReceiver.ConnectionNetworkListener {

    private var mService: MyServices? = null
    var mIsBound: Boolean? = null

    private lateinit var binding: ActivityMainBinding

    // TODO: 1
    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("START", "MAIN onCreate()")
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        registerReceiver(
            BroadReceiver(), IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        )

        with(binding) {
            startBtn.setOnClickListener {
                bindService()
                startService(Intent(this@MainActivity, MyServices::class.java))
            }

            StopBtn.setOnClickListener {

                stopService(Intent(this@MainActivity, MyServices::class.java))
                if (mIsBound == true) {
                    unbindService()
                    mIsBound = false
                }
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun showNetworkMessage(isConnected: Boolean) {
        if (!isConnected) {
            binding.textView.text = "You are offline"
        } else {
            binding.textView.text = "You are online"
        }
    }

    // TODO: 2
    private fun bindService() {
        Log.d("START", "MAIN bindService()")

        Intent(this, MyServices::class.java).also { intent ->
            bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)
        }
    }

    private val serviceConnection = object : ServiceConnection {
        // TODO: 8
        override fun onServiceConnected(className: ComponentName, iBinder: IBinder) {
            Log.d("START", "MAIN onServiceConnected()")


            val binder = iBinder as MyServices.MyBinder
            mIsBound = true
            mService = binder.service
        }


        override fun onServiceDisconnected(arg0: ComponentName) {
            Log.d("START", "MAIN onServiceDisconnected()")

            mIsBound = false
        }
    }


    // TODO:  9, 12
    private fun unbindService() {
        Log.d("START", "MAIN unbindService()")

        Intent(this, MyServices::class.java).also { intent ->
            unbindService(serviceConnection)
        }
    }

    // TODO: 11
    override fun onDestroy() {
        Log.d("START", "MAIN onDestroy()")

        super.onDestroy()
        unbindService()
    }

    override fun onNetwork(isConnected: Boolean) {
        showNetworkMessage(isConnected)
    }

    override fun onResume() {
        super.onResume()
        BroadReceiver.connect = this
    }
}