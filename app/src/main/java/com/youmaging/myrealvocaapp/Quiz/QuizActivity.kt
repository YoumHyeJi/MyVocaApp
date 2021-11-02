package com.youmaging.myrealvocaapp.Quiz

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.youmaging.myrealvocaapp.Tab.FlagViewModel
import com.youmaging.myrealvocaapp.databinding.ActivityQuizBinding


class QuizActivity : AppCompatActivity() {
    lateinit var binding : ActivityQuizBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuizBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    fun init(){
        binding.apply {
            quizVer1.setOnClickListener {
                val i: Intent = Intent(this@QuizActivity, SpecificQuizActivity::class.java)
                i.putExtra("quiz_num", "1")
                if(intent.hasExtra("flag")){
                    i.putExtra("flag", intent.getStringExtra("flag"))
                }
                if(intent.hasExtra("day")){
                    i.putExtra("day",intent.getStringExtra("day"))
                }
                startActivity(i)
            }
            quizVer2.setOnClickListener {
                val i: Intent = Intent(this@QuizActivity, SpecificQuizActivity::class.java)
                i.putExtra("quiz_num", "2")
                if(intent.hasExtra("flag")){
                    i.putExtra("flag", intent.getStringExtra("flag"))
                }
                if(intent.hasExtra("day")){
                    i.putExtra("day",intent.getStringExtra("day"))
                }
                startActivity(i)
            }
            quizVer3.setOnClickListener {
                val i: Intent = Intent(this@QuizActivity, SpecificQuizActivity::class.java)
                i.putExtra("quiz_num", "3")
                if(intent.hasExtra("flag")){
                    i.putExtra("flag", intent.getStringExtra("flag"))
                }
                if(intent.hasExtra("day")){
                    i.putExtra("day",intent.getStringExtra("day"))
                }
                startActivity(i)
            }
            quizVer4.setOnClickListener {
                val i: Intent = Intent(this@QuizActivity, SpecificQuizActivity::class.java)
                i.putExtra("quiz_num", "4")
                if(intent.hasExtra("flag")){
                    i.putExtra("flag", intent.getStringExtra("flag"))
                }
                if(intent.hasExtra("day")){
                    i.putExtra("day",intent.getStringExtra("day"))
                }
                startActivity(i)
            }
            quizVer5.setOnClickListener {
                val i: Intent = Intent(this@QuizActivity, SpecificQuizActivity::class.java)
                i.putExtra("quiz_num", "5")
                if(intent.hasExtra("flag")){
                    i.putExtra("flag", intent.getStringExtra("flag"))
                }
                if(intent.hasExtra("day")){
                    i.putExtra("day",intent.getStringExtra("day"))
                }
                startActivity(i)
            }
        }
    }

}