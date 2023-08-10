package com.saurabh.servicesdemo

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.OnClickListener
import androidx.appcompat.app.AppCompatActivity
import com.saurabh.servicesdemo.databinding.ActivityLocalBindingBinding
import com.saurabh.servicesdemo.service.IBinderService

class LocalBindingActivity : AppCompatActivity(), OnClickListener {

    val TAG = javaClass.name
    lateinit var binding: ActivityLocalBindingBinding
    lateinit var serviceIntent: Intent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLocalBindingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setClickListners()
        Log.e(TAG, "LocalBindingActivity Thread : ${Thread.currentThread().name}")
        serviceIntent = Intent(applicationContext, IBinderService::class.java)
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