package com.saurabh.servicesdemo

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.saurabh.servicesdemo.databinding.ActivityMyServiceBinding
import com.saurabh.servicesdemo.service.MyService

class MyServiceActivity : AppCompatActivity(), View.OnClickListener {

    val TAG = javaClass.name
    lateinit var binding: ActivityMyServiceBinding
    lateinit var serviceIntent: Intent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyServiceBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setClickListners()
        Log.e(TAG, "MyServiceActivity Thread : ${Thread.currentThread().name}")
        Toast.makeText(applicationContext,  "$TAG MyServiceActivity Thread : ${Thread.currentThread().name}", Toast.LENGTH_LONG).show()
        serviceIntent = Intent(applicationContext, MyService::class.java)
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