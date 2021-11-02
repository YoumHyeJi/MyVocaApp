package com.youmaging.myrealvocaapp

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.youmaging.myrealvocaapp.List.Word

class DBHelper(val context: Context) : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {
    companion object {
        //데이터베이스 이름 & 버전 정보
        val DB_NAME = "voca_db.db"
        val DB_VERSION = 1

        // words 테이블 정보
        val WORD_TABLE_NAME = "words"
        val WID = "wid"
        val WFlAG = "wflag"
        val WDAY = "wday"
        val WORD = "word"
        val MEANING = "meaning"
        val FAV = "fav"
    }

    //데이터베이스가 처음 생성될때 호출됨. 테이블 생성해줌
    override fun onCreate(db: SQLiteDatabase?) {
        var create_table = "create table if not exists $WORD_TABLE_NAME(" +
                "$WID integer primary key, " +
                "$WFlAG integer not null, " +
                "$WDAY integer not null, " +
                "$WORD text not null, " +
                "$MEANING text not null, " +
                "$FAV integer not null );"
        db!!.execSQL(create_table)
    }

    //데이터베이스의 버전이 바꼈을때 호출됨. 테이블 업데이트해줌.
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        var drop_table = "drop table if exists $WORD_TABLE_NAME;"
        db!!.execSQL(drop_table)
        onCreate(db)
    }

    fun initDataClassWord(items: ArrayList<Word>, flag: Int, day: Int) {
        //; 꼭 넣어주기
        val strsql = "select * from $WORD_TABLE_NAME where $WFlAG = '$flag' and $WDAY ='$day' ;"
        val db = readableDatabase

        val cursor: Cursor = db.rawQuery(strsql, null)
        cursor.moveToFirst()

        if (cursor.count == 0)
            return

        items.clear()
        do {
            items.add(
                Word(
                    cursor.getString(0).toInt(),
                    cursor.getString(1).toInt(),
                    cursor.getString(2).toInt(),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getString(5).toInt()
                )
            )
        } while (cursor.moveToNext())

        cursor.close()
        db.close()
    }

    fun initDataClassWord(items: ArrayList<Word>) {
        //; 꼭 넣어주기
        val strsql = "select * from $WORD_TABLE_NAME where $FAV = '1';"
        val db = readableDatabase

        val cursor: Cursor = db.rawQuery(strsql, null)
        cursor.moveToFirst()

        if (cursor.count == 0)
            return

        items.clear()
        do {
            items.add(
                Word(
                    cursor.getString(0).toInt(),
                    cursor.getString(1).toInt(),
                    cursor.getString(2).toInt(),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getString(5).toInt()
                )
            )
        } while (cursor.moveToNext())

        cursor.close()
        db.close()
    }



    fun changeFavorite(_isChecked: Boolean, _wid: Int) {
        val db = writableDatabase
        val values: ContentValues = ContentValues()

        //즐겨찾기 추가
        if (_isChecked) {
            values.put(FAV, 1)
            db.update(WORD_TABLE_NAME, values, "$WID=?", arrayOf(_wid.toString()))
        }
        //즐겨찾기 삭제
        else {
            values.put(FAV, 0)
            db.update(WORD_TABLE_NAME, values, "$WID=?", arrayOf(_wid.toString()))
        }

        db.close()
    }



}