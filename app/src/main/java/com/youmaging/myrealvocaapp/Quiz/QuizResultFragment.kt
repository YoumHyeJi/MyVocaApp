package com.youmaging.myrealvocaapp.Quiz

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.youmaging.myrealvocaapp.DBHelper
import com.youmaging.myrealvocaapp.List.Word
import com.youmaging.myrealvocaapp.List.WordActivity
import com.youmaging.myrealvocaapp.R
import com.youmaging.myrealvocaapp.databinding.FragmentQuizResultListBinding


class QuizResultFragment(val items : List<Word>, val quizResult : List<Boolean>) : Fragment() {

    private var columnCount = 1
    var binding : FragmentQuizResultListBinding? = null
    val quizViewModel: QuizViewModel by activityViewModels<QuizViewModel>()
    var quiz_num : Int = 1
    var flag : Int = 0
    var day : Int = 0
    lateinit var myadapter : MyQuizResultRecyclerViewAdapter
    lateinit var dbHelper : DBHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentQuizResultListBinding.inflate(inflater, container, false)
        init()
        return binding!!.root
    }

    fun init(){

        binding!!.apply {
            calculScore()

            qrRecyclerView.layoutManager = when {
                columnCount <= 1 -> LinearLayoutManager(context)
                else -> GridLayoutManager(context, columnCount)
            }
            dbHelper = DBHelper(requireActivity())

            myadapter = MyQuizResultRecyclerViewAdapter(items, quizResult)

            myadapter.listener = object : MyQuizResultRecyclerViewAdapter.onFavBtnClickListener{
                override fun favBtnClick(word: Word, _pos: Int) {
                    if (word.fav == 0) {
                        dbHelper.changeFavorite(true, word.wid)
                        Toast.makeText(requireActivity(), "즐겨찾기 추가", Toast.LENGTH_SHORT).show()
                        items[_pos].fav = 1
                        myadapter.changeItem(_pos, 1)

                    } else if(word.fav == 1) {
                        dbHelper.changeFavorite(false, word.wid)
                        Toast.makeText(requireActivity(), "즐겨찾기 삭제", Toast.LENGTH_SHORT).show()
                        items[_pos].fav = 0
                        myadapter.changeItem(_pos, 0)

                    }

                }
            }
            qrRecyclerView.adapter = myadapter

            qrWrongFavaddBtn.setOnClickListener {
                var count : Int = 0
                for(i in 0 until items.size){
                    if(quizResult[i] == false){
                        dbHelper.changeFavorite(true, items[i].wid)
                        items[i].fav = 1
                        myadapter.changeItem(i, 1)
                        count++
                    }
                }
                Toast.makeText(requireActivity(), "틀린 단어 "+ count.toString() +"개 즐겨찾기에 추가!", Toast.LENGTH_SHORT).show()
            }
            qrRequizBtn.setOnClickListener {
                quiz_num = quizViewModel.selectedQuiz.value!!
                changeFragment(quiz_num)
            }
            qrRestudyBtn.setOnClickListener {
                flag = quizViewModel.selectedFlag.value!!
                day = quizViewModel.selectedDay.value!!
                val intent = Intent(requireActivity(), WordActivity::class.java)
                intent!!.putExtra("flag",flag.toString())
                intent!!.putExtra("day", day.toString())
                startActivity(intent)
            }
        }
    }

    fun calculScore(){
        val size : Int = quizResult.size
        Log.i("size!!!",size.toString())
        var count : Int = 0
        for(i in 0 until size){
            if(quizResult[i] == true){
                count++
            }
        }
        val result : Int = (count*100/size).toInt()
        Log.i("size~~result", result.toString())
        binding!!.qrScorePrecent.text = result.toString() + "%"
        binding!!.qrScore.text = count.toString() + "/" + size.toString()
    }

    fun changeFragment(num : Int){
        when(num){
            1 ->{
                val fragment = requireActivity().supportFragmentManager.beginTransaction()
                val quiz1Fragment = Quiz1Fragment()
                fragment.replace(R.id.quiz_framelayout, quiz1Fragment)
                fragment.commit()
            }
            2 ->{
                val fragment = requireActivity().supportFragmentManager.beginTransaction()
                val quiz2Fragment = Quiz2Fragment()
                fragment.replace(R.id.quiz_framelayout, quiz2Fragment)
                fragment.commit()
            }
            3->{
                val fragment = requireActivity().supportFragmentManager.beginTransaction()
                val quiz3Fragment = Quiz3Fragment()
                fragment.replace(R.id.quiz_framelayout, quiz3Fragment)
                fragment.commit()
            }
            4->{
                val fragment = requireActivity().supportFragmentManager.beginTransaction()
                val quiz4Fragment = Quiz4Fragment()
                fragment.replace(R.id.quiz_framelayout, quiz4Fragment)
                fragment.commit()
            }
            5->{
                val fragment = requireActivity().supportFragmentManager.beginTransaction()
                val quiz5Fragment = Quiz5Fragment()
                fragment.replace(R.id.quiz_framelayout, quiz5Fragment)
                fragment.commit()
            }
            else->{
                val fragment = requireActivity().supportFragmentManager.beginTransaction()
                val quiz1Fragment = Quiz1Fragment()
                fragment.replace(R.id.quiz_framelayout, quiz1Fragment)
                fragment.commit()
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }


}