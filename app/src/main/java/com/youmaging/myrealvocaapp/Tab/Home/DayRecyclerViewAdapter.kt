package com.youmaging.myrealvocaapp.Tab.Home


import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.youmaging.myrealvocaapp.R


class DayRecyclerViewAdapter(
    private val values: List<String>
) : RecyclerView.Adapter<DayRecyclerViewAdapter.ViewHolder>() {

    var dayListener : onClickDayListener? = null

    interface onClickDayListener{
        fun listBtnClick(position : Int)
        fun quizBtnClick(position : Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_day, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item : String = values[position]
        holder.dayView.text = item

    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val dayView: TextView = view.findViewById(R.id.day)
        val listBtn: Button = view.findViewById(R.id.list_btn)
        val quizBtn : Button = view.findViewById(R.id.quiz_btn)
        init{
            dayView.setOnClickListener {
                dayListener!!.listBtnClick(bindingAdapterPosition)
            }
            listBtn.setOnClickListener {
                dayListener!!.listBtnClick(bindingAdapterPosition)
                //Log.i("selected Position","bindingAdapterPos = "+bindingAdapterPosition)
            }
            quizBtn.setOnClickListener {
                dayListener!!.quizBtnClick(bindingAdapterPosition)
            }
        }

    }
}