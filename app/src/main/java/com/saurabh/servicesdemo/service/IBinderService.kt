package com.saurabh.servicesdemo.service

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log
import com.saurabh.servicesdemo.R
import java.util.Random


// Service runs on main thread. Service don't have any UI means service runs without UI
class IBinderService : Service() {

    val TAG = javaClass.name

    private var mRandomNumber = 0
    private var mIsRandomGeneratorOn = false
    private val MIN = 0
    private val MAX = 100


    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    class IbinderDemo : Binder(){

    }

    override fun onDestroy() {
        super.onDestroy()
        Log.e(TAG, "onDestroy called Service Stopped")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.e(TAG, "onStartCommand Thread : ${Thread.currentThread().name}")
        Log.i(
            TAG,
            "In onStartCommend, thread id: " + Thread.currentThread().id
        )
        mIsRandomGeneratorOn = true
        Thread { startRandomNumberGenerator() }.start()
        return START_STICKY
    }

    private fun startRandomNumberGenerator() {
        while (mIsRandomGeneratorOn) {
            try {
                Thread.sleep(1000)
                if (mIsRandomGeneratorOn) {
                    mRandomNumber = Random().nextInt(MAX) + MIN
                    Log.i(
                        TAG,
                        "Thread id: " + Thread.currentThread().id + ", Random Number: " + mRandomNumber
                    )
                }
            } catch (e: InterruptedException) {
                Log.i(TAG, "Thread Interrupted")
            }
        }
    }

    private fun stopRandomNumberGenerator() {
        mIsRandomGeneratorOn = false
    }

    fun getRandomNumber(): Int {
        return mRandomNumber
    }
}