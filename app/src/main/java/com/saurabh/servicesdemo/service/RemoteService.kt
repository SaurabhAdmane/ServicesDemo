package com.saurabh.servicesdemo.service

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.Handler
import android.os.IBinder
import android.os.Message
import android.os.Messenger
import android.os.RemoteException
import android.util.Log
import android.widget.Toast
import java.util.Random

class RemoteService : Service() {

    val TAG = javaClass.name
    private var randomNumber = 0
    private var isRandomGeneratorOn = false
    private val GET_RANDOM_NUMBER_FLAG = 0
    private val MIN = 0
    private val MAX = 100
    private val randomNumberMessenger = Messenger(RandomNumberRequestHandler())

    override fun onBind(intent: Intent?): IBinder {
        Toast.makeText(applicationContext, "In onBind", Toast.LENGTH_LONG).show()
        Log.e(TAG, "In onBind")
        return randomNumberMessenger.binder
    }

    override fun onUnbind(intent: Intent?): Boolean {
        Toast.makeText(applicationContext, "In onUnbind", Toast.LENGTH_LONG).show()
        Log.e(TAG, "In onUnbind")
        return super.onUnbind(intent)
    }

    inner class RandomNumberRequestHandler : Handler() {
        override fun handleMessage(msg: Message) {
            when (msg.what) {
                GET_RANDOM_NUMBER_FLAG -> {
                    val messageDataSend = Message.obtain(null, GET_RANDOM_NUMBER_FLAG)
                    try {
                        messageDataSend.arg1 = getRandomNumber()
                        msg.replyTo.send(messageDataSend)
                    } catch (e: RemoteException) {
                        Log.e(TAG, e.message!!)
                    }
                }
            }
            super.handleMessage(msg)
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