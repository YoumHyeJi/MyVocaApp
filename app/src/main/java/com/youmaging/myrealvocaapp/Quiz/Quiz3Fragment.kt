package com.youmaging.myrealvocaapp.Quiz

import android.content.Intent
import android.graphics.Color
import android.opengl.Visibility
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.youmaging.myrealvocaapp.DBHelper
import com.youmaging.myrealvocaapp.List.Word
import com.youmaging.myrealvocaapp.R
import com.youmaging.myrealvocaapp.databinding.FragmentQuiz3Binding
import java.util.*
import kotlin.collections.ArrayList


class Quiz3Fragment : Fragment() {
    var binding: FragmentQuiz3Binding? = null
    val quizViewModel: QuizViewModel by activityViewModels<QuizViewModel>()
    lateinit var dbHelper: DBHelper
    val items = arrayListOf<Word>()
    val quizResult = arrayListOf<Boolean>()
    private val SPLASH_TIME_OUT: Long = 1000
    var order: Int = 0
    var flag: Int = 0
    var day: Int = 0

    val green : String = "#4CAF50"
    val red : String = "#F64782"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentQuiz3Binding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
        init()
    }

    fun initData() {
        dbHelper = DBHelper(requireActivity())
        flag = quizViewModel.selectedFlag.value!!
        day = quizViewModel.selectedDay.value!!
        dbHelper.initDataClassWord(items, flag!!, day!! + 1)
        Log.i("아이템 사이즈!!", items.size.toString())
        suffle(items)
    }

    fun init() {
        binding!!.apply {
            quiz3Day.text = "Day" + (day + 1).toString()
            quiz3Count.text = (order + 1).toString() + "/" + items.size.toString()
            if (items.size != 0) {
                quiz3Quiz.text = items[order].meaning
            }

            if(items[order].fav == 1){
                quiz3FavaddBtn.isChecked = true
            }
            else{
                quiz3FavaddBtn.isChecked = false
            }

            quiz3FavaddBtn.setOnClickListener {
                if (items[order].fav == 0) {
                    dbHelper.changeFavorite(true, items[order].wid)
                    Toast.makeText(requireActivity(), "즐겨찾기 추가", Toast.LENGTH_SHORT).show()
                    items[order].fav = 1
                } else {
                    dbHelper.changeFavorite(false, items[order].wid)
                    Toast.makeText(requireActivity(), "즐겨찾기 삭제", Toast.LENGTH_SHORT).show()
                    items[order].fav = 0
                }

            }
            quiz3PassBtn.setOnClickListener {
                playQuiz(2)
            }
            quiz3SubmitBtn.setOnClickListener {
                playQuiz(1)
            }
        }
    }

    fun playQuiz(num: Int) {
        binding!!.apply {
            quiz3Answer.visibility = View.VISIBLE
            quiz3Answer.text = items[order].word
            if (num == 1) {
                if (quiz3Edtext.text.toString() == items[order].word) {
                    quiz3Answer.setBackgroundColor(Color.parseColor(green))
                    quiz3Quiz.setBackgroundColor(Color.parseColor(green))
                    quizResult.add(order, true)
                } else {
                    quiz3Answer.setBackgroundColor(Color.parseColor(red))
                    quiz3Quiz.setBackgroundColor(Color.parseColor(red))
                    quizResult.add(order, false)
                }
            } else {
                quiz3Answer.setBackgroundColor(Color.parseColor(red))
                quiz3Quiz.setBackgroundColor(Color.parseColor(red))
                quizResult.add(order, false)
            }
            ++order
            if (order < (items.size)) {
                Handler().postDelayed({
                    quiz3Count.text = (order + 1).toString() + "/" + items.size.toString()
                    quiz3Quiz.text = items[order].meaning
                    quiz3Quiz.setBackgroundColor(Color.WHITE)
                    quiz3Edtext.text.clear()
                    quiz3Answer.visibility = View.INVISIBLE

                    if(items[order].fav == 1){
                        quiz3FavaddBtn.isChecked = true
                    }
                    else{
                        quiz3FavaddBtn.isChecked = false
                    }

                }, SPLASH_TIME_OUT)
            } else {
                changeFragment()
            }

        }
    }

    fun suffle(array: ArrayList<Word>) {
        val random = Random()
        for (i in 0 until items.size) {
            val num: Int = random.nextInt(items.size)
            val tmp: Word = array[num]
            array[num] = array[i]
            array[i] = tmp
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    fun changeFragment() {
        val fragment = requireActivity().supportFragmentManager.beginTransaction()
        val quizResultFragment = QuizResultFragment(items,quizResult)
        fragment.replace(R.id.quiz_framelayout, quizResultFragment)
        fragment.commit()
    }

}