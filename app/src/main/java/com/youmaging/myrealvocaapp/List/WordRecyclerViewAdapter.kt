package com.youmaging.myrealvocaapp.List

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.ToggleButton
import androidx.recyclerview.widget.RecyclerView
import com.youmaging.myrealvocaapp.databinding.WordBinding

class WordRecyclerViewAdapter(var items: ArrayList<Word>) :
    RecyclerView.Adapter<WordRecyclerViewAdapter.ViewHolder>() {

    var listener: onBtnClickListener? = null

    interface onBtnClickListener {
        fun favaddBtnClick(word: Word, _pos: Int)
        fun textClick(word: Word)
        fun wordsearchBtnClick(word:Word)
    }

    inner class ViewHolder(binding: WordBinding) : RecyclerView.ViewHolder(binding.root) {
        val textword: TextView = binding.word
        val textmeaning: TextView = binding.meaning
        val btnfavadd: ToggleButton = binding.favaddBtn
        val layout: LinearLayout = binding.wordtextlayout
        val btnwordsearch : ImageButton = binding.wordSearchBtn
        init {
            binding.apply {
                layout.setOnClickListener {
                    listener!!.textClick(items[bindingAdapterPosition])
                }

                btnwordsearch.setOnClickListener {
                    listener!!.wordsearchBtnClick(items[bindingAdapterPosition])
                }

            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = WordBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (items[position].fav == 1) {
            holder.btnfavadd.isChecked = true
        } else {
            holder.btnfavadd.isChecked = false
        }
        holder.textword.text = items[position].word
        holder.textmeaning.text = items[position].meaning

        holder.btnfavadd.setOnClickListener {

            listener!!.favaddBtnClick(items[position], position)
        }

    }

    fun removeItem(pos: Int) {

        items.removeAt(pos)
        notifyItemRemoved(pos)
        notifyItemRangeChanged(pos, items.size);
        Log.i("아이템 사이즈", items.size.toString())


    }


    fun changeItem(_pos: Int, _fav : Int){
        items[_pos].fav = _fav
        notifyItemChanged(_pos)
        //Log.i("adapter fav = ", items[_pos].fav.toString())
    }


}