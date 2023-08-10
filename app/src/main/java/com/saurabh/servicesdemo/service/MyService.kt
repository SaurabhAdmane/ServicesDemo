package com.saurabh.servicesdemo.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import android.widget.Toast

// Service runs on main thread. Service don't have any UI means service runs without UI
class MyService : Service() {

    val TAG = javaClass.name

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.e(TAG, "onDestroy called Service Stopped")
        Toast.makeText(applicationContext, "onDestroy called Service Stopped", Toast.LENGTH_LONG).show()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.e(TAG, "onStartCommand Thread : ${Thread.currentThread().name}")
        Toast.makeText(applicationContext,  "$TAG onStartCommand Thread : ${Thread.currentThread().name}", Toast.LENGTH_LONG).show()
//        stopSelf() // this method will trigger onDestroy
        return super.onStartCommand(intent, flags, startId)
    }
}