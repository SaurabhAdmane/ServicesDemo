package com.saurabh.servicesdemo

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.saurabh.servicesdemo.databinding.ActivityRemoteServiceBinding
import com.saurabh.servicesdemo.service.RemoteService

class RemoteServiceActivity:AppCompatActivity() , View.OnClickListener {

    val TAG = javaClass.name
    lateinit var binding: ActivityRemoteServiceBinding
    lateinit var serviceIntent: Intent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRemoteServiceBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setClickListners()
        Log.e(TAG, "RemoteServiceActivity Thread : ${Thread.currentThread().name}")
        Toast.makeText(applicationContext,  "$TAG RemoteServiceActivity Thread : ${Thread.currentThread().name}", Toast.LENGTH_LONG).show()
        serviceIntent = Intent(applicationContext, RemoteService::class.java)
    }

    private fun setClickListners() {
        binding.btnStopService.setOnClickListener(this)
        binding.btnStartService.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.btn_start_service -> {
                startService(serviceIntent)
            }

            R.id.btn_stop_service -> {
                stopService(serviceIntent) // this method will trigger onDestroy in service class
            }
        }

    }
}