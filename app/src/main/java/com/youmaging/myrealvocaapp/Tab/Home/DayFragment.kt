package com.youmaging.myrealvocaapp.Tab.Home

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.youmaging.myrealvocaapp.Quiz.QuizActivity
import com.youmaging.myrealvocaapp.R
import com.youmaging.myrealvocaapp.Tab.FlagViewModel
import com.youmaging.myrealvocaapp.List.WordActivity


/**
 * A fragment representing a list of Items.
 */
class DayFragment : Fragment() {
    val LIST_WORD_REQUEST = 100
    private var columnCount = 1

    val items = arrayListOf<String>("DAY 1","DAY 2","DAY 3","DAY 4","DAY 5",
        "DAY 6","DAY 7","DAY 8","DAY 9","DAY 10")

    val flagViewModel : FlagViewModel by activityViewModels<FlagViewModel>()
    var intent : Intent? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_day_list, container, false)
        val myAdapter = DayRecyclerViewAdapter(items)
        myAdapter.dayListener = object : DayRecyclerViewAdapter.onClickDayListener{

            override fun listBtnClick(position: Int) {
                flagViewModel.setLiveDay(position)
                val flag : Int = flagViewModel.selectedFlag.value!!
                val day : Int = flagViewModel.selectedDay.value!!

                intent = Intent(requireActivity(), WordActivity::class.java)
                intent!!.putExtra("flag",flag.toString())
                intent!!.putExtra("day", day.toString())
                startActivity(intent)
            }

            override fun quizBtnClick(position: Int) {
                flagViewModel.setLiveDay(position)
                val flag : Int = flagViewModel.selectedFlag.value!!
                val day : Int = flagViewModel.selectedDay.value!!

                intent = Intent(requireActivity(), QuizActivity::class.java)
                intent!!.putExtra("flag",flag.toString())
                intent!!.putExtra("day", day.toString())
                startActivity(intent)
            }

        }
        // Set the adapter
        if (view is RecyclerView) {
            with(view) {
                layoutManager = when {
                    columnCount <= 1 -> LinearLayoutManager(context)
                    else -> GridLayoutManager(context, columnCount)
                }
                adapter = myAdapter
            }
        }
        return view
    }

}