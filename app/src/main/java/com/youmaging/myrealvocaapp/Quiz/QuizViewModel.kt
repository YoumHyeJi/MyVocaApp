package com.youmaging.myrealvocaapp.Quiz

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class QuizViewModel : ViewModel(){
    val selectedQuiz = MutableLiveData<Int>()

    val selectedFlag = MutableLiveData<Int>()

    val selectedDay = MutableLiveData<Int>()

    fun setLiveQuiz(num: Int){
        selectedQuiz.value = num
        Log.i("Quiz", selectedQuiz.value.toString())
    }

    fun setLiveFlag(num: Int){
        selectedFlag.value = num
        Log.i("flag", selectedFlag.value.toString())
    }

    fun setLiveDay(num: Int){
        selectedDay.value = num
        Log.i("day", selectedDay.value.toString())
    }
}