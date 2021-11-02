package com.youmaging.myrealvocaapp.Tab.Favortie

import android.app.SearchManager
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.speech.tts.TextToSpeech
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.graphics.createBitmap
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.ItemTouchHelper
import com.youmaging.myrealvocaapp.DBHelper
import com.youmaging.myrealvocaapp.List.Word
import com.youmaging.myrealvocaapp.List.WordRecyclerViewAdapter
import com.youmaging.myrealvocaapp.Tab.FlagViewModel
import com.youmaging.myrealvocaapp.R
import java.io.FileOutputStream
import java.util.*


class FavoriteFragment : Fragment() {

    lateinit var myadapter: WordRecyclerViewAdapter
    lateinit var dbHelper : DBHelper

    val items = arrayListOf<Word>()
    private var columnCount = 1


    lateinit var tts : TextToSpeech
    var isTtsReady = false


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_favorite_list, container, false)

        initTTS()
        initDB()

        //items 초기화
        dbHelper = DBHelper(requireActivity())
        dbHelper.initDataClassWord(items)

        //adapter 초기화
        myadapter = WordRecyclerViewAdapter(items)

        myadapter.listener = object : WordRecyclerViewAdapter.onBtnClickListener{

            override fun favaddBtnClick(word: Word, _pos:Int) {
                dbHelper.changeFavorite(false, word.wid)
                Toast.makeText(requireActivity(), "즐겨찾기 삭제", Toast.LENGTH_SHORT).show()
                myadapter.removeItem(_pos)
            }

            override fun textClick(word: Word) {
                Toast.makeText(requireActivity(), word.meaning, Toast.LENGTH_SHORT).show()
                if(isTtsReady)
                    tts.speak(word.word, TextToSpeech.QUEUE_ADD, null, null)

            }

            override fun wordsearchBtnClick(word: Word) {
                val textWord : String = word.word
                val texturl : String = "https://en.dict.naver.com/#/search?query=" + textWord
                val webpage = Uri.parse(texturl)
                val webIntent = Intent(Intent.ACTION_VIEW, webpage)
                startActivity(webIntent)

                /*
                val textWord : String = word.word
                val webIntent = Intent(Intent.ACTION_WEB_SEARCH)
                webIntent.putExtra(SearchManager.QUERY, textWord)
                startActivity(webIntent)
                */
            }

        }



    // Set the adapter
        if (view is RecyclerView) {
            with(view) {
                layoutManager = when {
                    columnCount <= 1 -> LinearLayoutManager(context)
                    else -> GridLayoutManager(context, columnCount)
                }
                adapter = myadapter

            }
        }
        return view
    }

    fun initDB(){
        val dbfile = requireActivity().getDatabasePath("voca_db.db")
        if(!dbfile.parentFile.exists()){
            dbfile.parentFile.mkdir()
        }
        if(!dbfile.exists()){
            val file = resources.openRawResource(R.raw.voca_db)
            val fileSize = file.available()
            val buffer = ByteArray(fileSize)
            file.read(buffer)
            file.close()
            dbfile.createNewFile()
            val output = FileOutputStream(dbfile)
            output.write(buffer)
            output.close()
        }
    }

    fun initTTS(){
        tts = TextToSpeech(requireActivity(), TextToSpeech.OnInitListener {
            isTtsReady = true
            tts.language = Locale.US
        })
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onStop(){
        super.onStop()
        tts.stop()
    }

    override fun onDestroy() {
        super.onDestroy()
        tts.shutdown()
    }


}