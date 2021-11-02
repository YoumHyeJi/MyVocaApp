package com.youmaging.myrealvocaapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.youmaging.myrealvocaapp.databinding.ActivityLoadingBinding
import com.youmaging.myrealvocaapp.databinding.ActivityMainBinding

class LoadingActivity : AppCompatActivity() {
    private val SPLASH_TIME_OUT:Long = 2000
    lateinit var binding : ActivityLoadingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoadingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    fun init(){
        Handler().postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        },SPLASH_TIME_OUT)
    }
}