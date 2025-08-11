package com.example.karaokevietnam.data

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DbHelper(ctx: Context) : SQLiteOpenHelper(ctx, "arirang.sqlite", null, 1) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("""
            CREATE TABLE IF NOT EXISTS ArirangSongList(
                _id INTEGER PRIMARY KEY AUTOINCREMENT,
                MABH TEXT,
                TENBH1 TEXT,
                LOIBH TEXT,
                TACGIA TEXT,
                THELOAI TEXT,
                YEUTHICH INTEGER DEFAULT 0
            )
        """.trimIndent())

        // seed demo
        db.execSQL("""
            INSERT INTO ArirangSongList(MABH,TENBH1,LOIBH,TACGIA,THELOAI,YEUTHICH) VALUES
            ('52300','Em là ai em là ai','Lời 1…','Nhạc Hoa','Trữ tình',0),
            ('51548','Say tình','Lời 2…','Quốc Bảo','Nhạc trẻ',0),
            ('57689','Hát với dòng sông','Lời 3…','Phó Đức Phương','Trữ tình',1),
            ('50194','Xuân và tuổi trẻ','Lời 4…','La Hối','Nhạc xuân',0)
        """.trimIndent())
    }
    override fun onUpgrade(db: SQLiteDatabase, old: Int, new: Int) {}
}