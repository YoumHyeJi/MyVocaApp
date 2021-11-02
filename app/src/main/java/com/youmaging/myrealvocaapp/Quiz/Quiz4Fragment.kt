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
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.youmaging.myrealvocaapp.DBHelper
import com.youmaging.myrealvocaapp.List.Word
import com.youmaging.myrealvocaapp.R
import com.youmaging.myrealvocaapp.databinding.FragmentQuiz3Binding
import com.youmaging.myrealvocaapp.databinding.FragmentQuiz4Binding
import java.util.*
import kotlin.collections.ArrayList

class Quiz4Fragment : Fragment() {
    var binding: FragmentQuiz4Binding? = null
    val quizViewModel: QuizViewModel by activityViewModels<QuizViewModel>()
    lateinit var dbHelper: DBHelper
    val items = arrayListOf<Word>()
    val quizResult = arrayListOf<Boolean>()
    private val SPLASH_TIME_OUT: Long = 1000
    var order: Int = 0
    var flag: Int = 0
    var day: Int = 0

    lateinit var tts: TextToSpeech
    var isTtsReady = false

    val green : String = "#4CAF50"
    val red : String = "#F64782"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentQuiz4Binding.inflate(inflater, container, false)
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
        Log.i("아이템 사이즈!!", items.size.toString())
        suffle(items)
    }

    fun init() {
        binding!!.apply {
            quiz4Day.text = "Day" + (day + 1).toString()
            quiz4Count.text = (order + 1).toString() + "/" + items.size.toString()

            if ((items.size != 0) && (isTtsReady == true)) {
                tts.speak(items[order].word, TextToSpeech.QUEUE_ADD, null, null)
            }

            if (items[order].fav == 1) {
                quiz4FavaddBtn.isChecked = true
            } else {
                quiz4FavaddBtn.isChecked = false
            }

            quiz4Speaker.setOnClickListener {
                if (isTtsReady)
                    tts.speak(items[order].word, TextToSpeech.QUEUE_ADD, null, null)
            }

            quiz4FavaddBtn.setOnClickListener {
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
            quiz4PassBtn.setOnClickListener {
                playQuiz(2)
            }
            quiz4SubmitBtn.setOnClickListener {
                playQuiz(1)
            }

        }
    }

    fun playQuiz(num: Int) {
        binding!!.apply {
            quiz4Word.visibility = View.VISIBLE
            quiz4Word.text = items[order].word
            quiz4Meaning.visibility = View.VISIBLE
            quiz4Meaning.text = items[order].meaning

            if (num == 1) {
                if (quiz4Edtext.text.toString() == items[order].word) {
                    quiz4Meaning.setBackgroundColor(Color.parseColor(green))
                    quiz4Word.setBackgroundColor(Color.parseColor(green))
                    quizResult.add(order, true)
                } else {
                    quiz4Word.setBackgroundColor(Color.parseColor(red))
                    quiz4Meaning.setBackgroundColor(Color.parseColor(red))
                    quizResult.add(order, false)
                }
            } else {
                quiz4Meaning.setBackgroundColor(Color.parseColor(red))
                quiz4Word.setBackgroundColor(Color.parseColor(red))
                quizResult.add(order, false)
            }

            ++order
            if (order < (items.size)) {
                Handler().postDelayed({
                    quiz4Count.text = (order + 1).toString() + "/" + items.size.toString()

                    if (isTtsReady)
                        tts.speak(items[order].word, TextToSpeech.QUEUE_ADD, null, null)

                    quiz4Edtext.text.clear()
                    quiz4Meaning.visibility = View.INVISIBLE
                    quiz4Word.visibility = View.INVISIBLE

                    if (items[order].fav == 1) {
                        quiz4FavaddBtn.isChecked = true
                    } else {
                        quiz4FavaddBtn.isChecked = false
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
        val quizResultFragment = QuizResultFragment(items, quizResult)
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