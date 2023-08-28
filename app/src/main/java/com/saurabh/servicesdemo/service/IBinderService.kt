package com.saurabh.servicesdemo.service

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import java.util.Random


// Service runs on main thread. Service don't have any UI means service runs without UI
class IBinderService : Service() {

    val TAG = javaClass.name
    private var randomNumber = 0
    private var isRandomGeneratorOn = false
    private val MIN = 0
    private val MAX = 100
    private val iBinder = MyServiceBinder()

    override fun onBind(intent: Intent?): IBinder {
        Toast.makeText(applicationContext, "In onBind", Toast.LENGTH_LONG).show()
        Log.e(TAG, "In onBind")
        return iBinder
    }

    override fun onUnbind(intent: Intent?): Boolean {
        Toast.makeText(applicationContext, "In onUnbind", Toast.LENGTH_LONG).show()
        Log.e(TAG, "In onUnbind")
        return super.onUnbind(intent)
    }

    inner class MyServiceBinder : Binder() {
        fun getService(): IBinderService {
            return this@IBinderService
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        stopRandomNumberGenerator()
        Toast.makeText(applicationContext, "In onDestroy", Toast.LENGTH_LONG).show()
        Log.e(TAG, "onDestroy called Service Stopped")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Toast.makeText(applicationContext, "In onStartCommand", Toast.LENGTH_LONG).show()
        Log.e(TAG, "onStartCommand Thread : ${Thread.currentThread().name}")
        Log.e(
            TAG, "onStartCommend, thread id: " + Thread.currentThread().id
        )
        isRandomGeneratorOn = true
        Thread { startRandomNumberGenerator() }.start()
        return START_STICKY
    }

    private fun startRandomNumberGenerator() {
        while (isRandomGeneratorOn) {
            try {
                Thread.sleep(1000)
                if (isRandomGeneratorOn) {
                    randomNumber = Random().nextInt(MAX) + MIN
                    Log.e(
                        TAG,
                        "Thread id: " + Thread.currentThread().id + ", Random Number: " + randomNumber
                    )
                }
            } catch (e: InterruptedException) {
                Log.e(TAG, "Thread Interrupted")
            }
        }
    }

    private fun stopRandomNumberGenerator() {
        isRandomGeneratorOn = false
    }

    fun getRandomNumber(): Int {
        return randomNumber
    }
}