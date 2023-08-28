package com.saurabh.servicesdemo

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import androidx.appcompat.app.AppCompatActivity
import com.saurabh.servicesdemo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), OnClickListener {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setClickListners()
    }

    private fun setClickListners() {
        binding.btnService.setOnClickListener(this)
        binding.btnIbinderService.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_service -> startActivity(
                Intent(
                    applicationContext, MyServiceActivity::class.java
                )
            )

            R.id.btn_ibinder_service -> startActivity(
                Intent(
                    applicationContext, IBindingActivity::class.java
                )
            )
        }
    }
}