package com.youmaging.myrealvocaapp.Tab

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class FlagViewModel : ViewModel() {
    val selectedFlag = MutableLiveData<Int>()

    val selectedDay = MutableLiveData<Int>()

    fun setLiveFlag(num: Int){
        selectedFlag.value = num
        Log.i("flag", selectedFlag.value.toString())
    }

    fun setLiveDay(num: Int){
        selectedDay.value = num
        Log.i("day", selectedDay.value.toString())
    }
}