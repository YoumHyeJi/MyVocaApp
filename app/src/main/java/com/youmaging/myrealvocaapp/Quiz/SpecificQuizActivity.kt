package com.youmaging.myrealvocaapp.Quiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.youmaging.myrealvocaapp.R

import com.youmaging.myrealvocaapp.databinding.ActivitySpecificQuizBinding

class SpecificQuizActivity : AppCompatActivity() {
    lateinit var binding : ActivitySpecificQuizBinding
    val quizViewModel : QuizViewModel by viewModels<QuizViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySpecificQuizBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    fun init(){
        if(intent.hasExtra("flag")){
            val flag_num = intent.getStringExtra("flag")!!.toInt()
            quizViewModel.setLiveFlag(flag_num)
        }
        if(intent.hasExtra("day")){
            val day_num = intent.getStringExtra("day")!!.toInt()
            quizViewModel.setLiveDay(day_num)
        }
        if(intent.hasExtra("quiz_num")){
            val quiz_num = intent.getStringExtra("quiz_num")!!.toInt()
            quizViewModel.setLiveQuiz(quiz_num)
            changeFragment(quizViewModel.selectedQuiz.value!!)
        }
    }


    fun changeFragment(num : Int){
        when(num){
            1 ->{
                val fragment = supportFragmentManager.beginTransaction()
                val quiz1Fragment = Quiz1Fragment()
                fragment.replace(R.id.quiz_framelayout, quiz1Fragment)
                fragment.commit()
            }
            2 ->{
                val fragment = supportFragmentManager.beginTransaction()
                val quiz2Fragment = Quiz2Fragment()
                fragment.replace(R.id.quiz_framelayout, quiz2Fragment)
                fragment.commit()
            }
            3->{
                val fragment = supportFragmentManager.beginTransaction()
                val quiz3Fragment = Quiz3Fragment()
                fragment.replace(R.id.quiz_framelayout, quiz3Fragment)
                fragment.commit()
            }
            4->{
                val fragment = supportFragmentManager.beginTransaction()
                val quiz4Fragment = Quiz4Fragment()
                fragment.replace(R.id.quiz_framelayout, quiz4Fragment)
                fragment.commit()
            }
            5->{
                val fragment = supportFragmentManager.beginTransaction()
                val quiz5Fragment = Quiz5Fragment()
                fragment.replace(R.id.quiz_framelayout, quiz5Fragment)
                fragment.commit()
            }
            else->{
                val fragment = supportFragmentManager.beginTransaction()
                val quiz1Fragment = Quiz1Fragment()
                fragment.replace(R.id.quiz_framelayout, quiz1Fragment)
                fragment.commit()
            }
        }

    }
}