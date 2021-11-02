package com.youmaging.myrealvocaapp.List

import android.app.SearchManager
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.youmaging.myrealvocaapp.DBHelper
import com.youmaging.myrealvocaapp.R
import com.youmaging.myrealvocaapp.Tab.FlagViewModel

import com.youmaging.myrealvocaapp.databinding.WordListBinding
import java.io.FileOutputStream
import java.util.*

class WordActivity : AppCompatActivity() {
    val flagViewModel: FlagViewModel by viewModels<FlagViewModel>()
    lateinit var myadapter: WordRecyclerViewAdapter
    lateinit var dbHelper: DBHelper

    var items = arrayListOf<Word>()
    lateinit var binding: WordListBinding

    lateinit var tts: TextToSpeech
    var isTtsReady = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = WordListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initTTS()
        //DBHelper 생성하기 전에 initDB() 해주기
        initDB()
        init()
    }


    fun init() {
        dbHelper = DBHelper(this)
        var flag: String = "-1"
        var day: String = "-1"
        if (intent.hasExtra("flag"))
            flag = intent.getStringExtra("flag")!!
        if (intent.hasExtra("day"))
            day = intent.getStringExtra("day")!!

        dbHelper.initDataClassWord(items, flag.toInt(), day.toInt() + 1)

        binding!!.apply {
            wordRecyclerview.layoutManager =
                LinearLayoutManager(this@WordActivity, LinearLayoutManager.VERTICAL, false)


            myadapter = WordRecyclerViewAdapter(items)
            myadapter.listener = object : WordRecyclerViewAdapter.onBtnClickListener {
                override fun favaddBtnClick(word: Word, _pos: Int) {
                    if (word.fav == 0) {
                        dbHelper.changeFavorite(true, word.wid)
                        Toast.makeText(this@WordActivity, "즐겨찾기 추가", Toast.LENGTH_SHORT).show()
                        myadapter.changeItem(_pos, 1)
                        //Log.i("activity fav (추가) = ", items[_pos].fav.toString())

                    } else if (word.fav == 1) {
                        dbHelper.changeFavorite(false, word.wid)
                        Toast.makeText(this@WordActivity, "즐겨찾기 삭제", Toast.LENGTH_SHORT).show()
                        myadapter.changeItem(_pos, 0)
                        //Log.i("activity fav (삭제) = ", items[_pos].fav.toString())
                    }


                }

                override fun textClick(word: Word) {
                    Toast.makeText(this@WordActivity, word.meaning, Toast.LENGTH_SHORT).show()
                    if (isTtsReady)
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

            wordRecyclerview.adapter = myadapter
        }

    }

    fun initTTS() {
        tts = TextToSpeech(this@WordActivity, TextToSpeech.OnInitListener {
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

    fun initDB() {
        val dbfile = getDatabasePath("voca_db.db")
        if (!dbfile.parentFile.exists()) {
            dbfile.parentFile.mkdir()
        }
        if (!dbfile.exists()) {
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
}