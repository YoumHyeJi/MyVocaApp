package com.youmaging.myrealvocaapp.Quiz

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.ToggleButton
import androidx.viewbinding.ViewBinding
import com.youmaging.myrealvocaapp.List.Word
import com.youmaging.myrealvocaapp.R
import com.youmaging.myrealvocaapp.databinding.FragmentQuizResultBinding


class MyQuizResultRecyclerViewAdapter(
    private val items: List<Word>, private val quizResult : List<Boolean>
) : RecyclerView.Adapter<MyQuizResultRecyclerViewAdapter.ViewHolder>() {

    var listener : onFavBtnClickListener? = null

    interface onFavBtnClickListener {
        fun favBtnClick(word: Word, _pos: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = FragmentQuizResultBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.word.text = items[position].word
        holder.meaning.text = items[position].meaning
        if(quizResult[position] == true){
            holder.resultImage.setImageResource(R.drawable.ic_baseline_mood_24)
        }
        else{
            holder.resultImage.setImageResource(R.drawable.ic_baseline_mood_bad_24)
        }

        if (items[position].fav == 1) {
            holder.favAddBtn.isChecked = true
        } else {
            holder.favAddBtn.isChecked = false
        }

        holder.favAddBtn.setOnClickListener {
            listener!!.favBtnClick(items[position], position)
        }
    }

    override fun getItemCount(): Int = items.size

    inner class ViewHolder(binding: FragmentQuizResultBinding) : RecyclerView.ViewHolder(binding.root) {
        val word : TextView = binding.qrWord
        val meaning : TextView = binding.qrMeaning
        val favAddBtn : ToggleButton = binding.qrFavaddBtn
        val resultImage : ImageView = binding.qrResultImage
        init{

        }

    }

    fun changeItem(_pos: Int, _fav : Int){
        items[_pos].fav = _fav
        notifyItemChanged(_pos)
    }


}