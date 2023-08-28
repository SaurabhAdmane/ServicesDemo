package com.saurabh.servicesdemo

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.view.View
import android.view.View.OnClickListener
import androidx.appcompat.app.AppCompatActivity
import com.saurabh.servicesdemo.databinding.ActivityIBindingBinding
import com.saurabh.servicesdemo.service.IBinderService

// Also call Local Binding
class IBindingActivity : AppCompatActivity(), OnClickListener {

    private var isServiceBound = false
    val TAG = javaClass.name
    lateinit var binding: ActivityIBindingBinding
    lateinit var serviceIntent: Intent
    private var serviceConnection: ServiceConnection? = null
    lateinit var iBinderService: IBinderService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIBindingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setClickListners()
        Log.e(TAG, "IBindingActivity Thread : ${Thread.currentThread().name}")
        serviceIntent = Intent(applicationContext, IBinderService::class.java)
    }

    private fun setClickListners() {
        binding.btnStopService.setOnClickListener(this)
        binding.btnStartService.setOnClickListener(this)
        binding.btnBindService.setOnClickListener(this)
        binding.btnUnbindService.setOnClickListener(this)
        binding.btnRandomNumber.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.btn_start_service -> {
                // this will start the service
                startService(serviceIntent)
            }

            R.id.btn_stop_service -> {
                // This will stop the service, this method will trigger onDestroy in service class
                /**
                 * if the service is bound to any component then service will not stop, to stop the service you have to unbound all component which is bound to the service
                 */
                stopService(serviceIntent)
            }

            R.id.btn_bind_service -> {
                /**
                 * this method call onBind method of service class. If the service is already bind then this method will not get execute
                 * OnBind method of service class will get executed only onces
                 */
                bindService()
            }

            R.id.btn_unbind_service -> {
                unBindService()
            }

            R.id.btn_random_number -> {
                // get random number from service and display it to the UI textview
                /**
                 * if the service is bound then only you can read data from service.
                 * To read the data from service, that service should be bounded
                 */
                setRandomNumberToTextvView()
            }
        }
    }

    fun bindService() {
        if (serviceConnection == null) {
            serviceConnection = object : ServiceConnection {
                override fun onServiceConnected(componentName: ComponentName, iBinder: IBinder) {
                    val myServiceBinder: IBinderService.MyServiceBinder =
                        iBinder as IBinderService.MyServiceBinder
                    iBinderService = myServiceBinder.getService()
                    isServiceBound = true
                }

                override fun onServiceDisconnected(componentName: ComponentName) {
                    isServiceBound = false
                }
            }
        }
        bindService(serviceIntent, serviceConnection!!, Context.BIND_AUTO_CREATE)
    }

    fun unBindService() {
        if (isServiceBound) {
            unbindService(serviceConnection!!)
            isServiceBound = false
        }
    }

    private fun setRandomNumberToTextvView() {
        if (isServiceBound)
            binding.txtStatus.text =
                "Random number from service : ${iBinderService.getRandomNumber()}"
        else
            binding.txtStatus.text = "Service not bound"
    }
}