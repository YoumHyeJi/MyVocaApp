package com.youmaging.myrealvocaapp.Quiz

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.speech.tts.TextToSpeech
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.youmaging.myrealvocaapp.DBHelper
import com.youmaging.myrealvocaapp.List.Word
import com.youmaging.myrealvocaapp.R
import com.youmaging.myrealvocaapp.databinding.FragmentQuiz1Binding
import com.youmaging.myrealvocaapp.databinding.FragmentQuiz5Binding
import java.util.*
import kotlin.collections.ArrayList

class Quiz5Fragment : Fragment() {
    var binding: FragmentQuiz5Binding? = null
    val quizViewModel: QuizViewModel by activityViewModels<QuizViewModel>()
    lateinit var dbHelper: DBHelper
    val items = arrayListOf<Word>()
    val quizResult = arrayListOf<Boolean>()
    private val SPLASH_TIME_OUT: Long = 1000
    var order: Int = 0
    var flag: Int = 0
    var day: Int = 0
    var right_index : Int = 0
    val buttonList = arrayListOf<Button>()

    lateinit var tts: TextToSpeech
    var isTtsReady = false

    val green : String = "#4CAF50"
    val red : String = "#F64782"


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentQuiz5Binding.inflate(inflater, container, false)
        return binding!!.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initTTS()
        initData()
        init()
    }

    fun initData() {
        dbHelper = DBHelper(requireActivity())
        flag = quizViewModel.selectedFlag.value!!
        day = quizViewModel.selectedDay.value!!
        dbHelper.initDataClassWord(items, flag!!, day!! + 1)
        suffle(items)
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

    fun init() {
        binding!!.apply {
            buttonList.add(0,quiz5Answer1Btn)
            buttonList.add(1,quiz5Answer2Btn)
            buttonList.add(2,quiz5Answer3Btn)

            if (items.size != 0) {
                quiz5Day.text = "Day" + (day + 1).toString()
                setQuiz()
            }

            quiz5Speaker.setOnClickListener {
                if (isTtsReady)
                    tts.speak(items[order].word, TextToSpeech.QUEUE_ADD, null, null)
            }


            quiz5FavaddBtn.setOnClickListener {
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
            quiz5PassBtn.setOnClickListener {
                playQuiz(-1)
            }

            quiz5Answer1Btn.setOnClickListener {
                playQuiz(0)
            }
            quiz5Answer2Btn.setOnClickListener {
                playQuiz(1)
            }
            quiz5Answer3Btn.setOnClickListener {
                playQuiz(2)
            }
        }
    }

    fun setQuiz(){

        val random = Random()
        //정답 위치
        right_index = random.nextInt(3)
        var num1 : Int
        var num2 : Int
        Log.i("num", items.size.toString())
        do{
            num1 = random.nextInt(items.size)
            num2 = random.nextInt(items.size)
        } while((num1 == order) || (num2 == order) || (num1==num2))
        Log.i("num", num1.toString() +"&"+ num2.toString())

        binding!!.apply {
            quiz5Count.text = (order + 1).toString() + "/" + items.size.toString()

            if(items[order].fav == 1){
                quiz5FavaddBtn.isChecked = true
            }
            else{
                quiz5FavaddBtn.isChecked = false
            }

            when(right_index){
                0->{
                    quiz5Answer1Btn.setText(items[order].meaning)
                    quiz5Answer2Btn.setText(items[num1].meaning)
                    quiz5Answer3Btn.setText(items[num2].meaning)
                }
                1->{
                    quiz5Answer1Btn.setText(items[num1].meaning)
                    quiz5Answer2Btn.setText(items[order].meaning)
                    quiz5Answer3Btn.setText(items[num2].meaning)
                }
                2->{
                    quiz5Answer1Btn.setText(items[num1].meaning)
                    quiz5Answer2Btn.setText(items[num2].meaning)
                    quiz5Answer3Btn.setText(items[order].meaning)
                }
            }
        }

        if (isTtsReady)
            tts.speak(items[order].word, TextToSpeech.QUEUE_ADD, null, null)
    }

    fun playQuiz(num: Int) {
        binding!!.apply {

            quiz5Answer.visibility = View.VISIBLE
            quiz5Answer.setText(items[order].word)

            if(num<0){
                buttonList[0].setBackgroundColor(Color.parseColor(red))
                buttonList[1].setBackgroundColor(Color.parseColor(red))
                buttonList[2].setBackgroundColor(Color.parseColor(red))
                buttonList[right_index].setBackgroundColor(Color.parseColor(green))
                quizResult.add(order, false)
            }
            else {
                if (buttonList[num].text.toString() == items[order].meaning) {
                    buttonList[num].setBackgroundColor(Color.parseColor(green))
                    quizResult.add(order, true)
                } else {
                    buttonList[num].setBackgroundColor(Color.parseColor(red))
                    quizResult.add(order, false)
                    buttonList[right_index].setBackgroundColor(Color.parseColor(green))
                }
            }

            order++
            if (order < items.size) {
                Handler().postDelayed({
                    quiz5Answer.visibility = View.INVISIBLE
                    buttonList[0].setBackgroundColor(Color.WHITE)
                    buttonList[1].setBackgroundColor(Color.WHITE)
                    buttonList[2].setBackgroundColor(Color.WHITE)
                    setQuiz()
                }, SPLASH_TIME_OUT)
            } else {
                changeFragment()
            }

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

    fun initTTS() {
        tts = TextToSpeech(requireActivity(), TextToSpeech.OnInitListener {
            isTtsReady = true
            tts.language = Locale.US
        })
    }

    override fun onStop() {
        super.onStop()
        tts.stop()
    }

    override fun onDestroy() {
        super.onDestroy()
        tts.shutdown()
    }

}