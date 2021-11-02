package com.youmaging.myrealvocaapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels

import com.google.android.material.tabs.TabLayoutMediator
import com.youmaging.myrealvocaapp.Tab.FlagViewModel
import com.youmaging.myrealvocaapp.Tab.MyFragStateAdapter
import com.youmaging.myrealvocaapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    val flagViewModel : FlagViewModel by viewModels<FlagViewModel>()
    lateinit var binding : ActivityMainBinding
    val textarr = arrayListOf<String>("홈", "즐겨찾기")
    val iconarr = arrayListOf<Int>(R.drawable.ic_baseline_home_24, R.drawable.ic_baseline_star_outline_24)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    fun init(){
        binding.viewPager.adapter =
            MyFragStateAdapter(this)
        TabLayoutMediator(binding.tabLayout, binding.viewPager){
            tab,position->
            tab.text = textarr[position]
            tab.setIcon(iconarr[position])
        }.attach()

    }

    override fun onResume() {
        super.onResume()
        init()
    }

}