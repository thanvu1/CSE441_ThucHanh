package com.example.karaokevietnam

import android.content.ContentValues
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class SubActivity : AppCompatActivity() {

    private lateinit var txtmaso: TextView
    private lateinit var txtbaihat: TextView
    private lateinit var txtloibaihat: TextView
    private lateinit var txttacgia: TextView
    private lateinit var btnthich: ImageButton
    private lateinit var btnkhongthich: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.subactivity)

        txtmaso = findViewById(R.id.txtmaso)
        txtbaihat = findViewById(R.id.txtbaihat)
        txtloibaihat = findViewById(R.id.txtloibaihat)
        txttacgia = findViewById(R.id.txttacgia)
        btnthich = findViewById(R.id.btnthich)
        btnkhongthich = findViewById(R.id.btnkhongthich)

        val maso = intent.getStringExtra("maso") ?: return
        txtmaso.text = "#$maso"

        val c = MainActivity.database.rawQuery(
            "SELECT * FROM ArirangSongList WHERE MABH=?",
            arrayOf(maso)
        )
        if (c.moveToFirst()) {
            txtbaihat.text = c.getString(2)
            txtloibaihat.text = c.getString(3)
            txttacgia.text = c.getString(4)
            val like = c.getInt(6)
            if (like == 1) { btnthich.visibility = View.VISIBLE; btnkhongthich.visibility = View.INVISIBLE }
            else { btnthich.visibility = View.INVISIBLE; btnkhongthich.visibility = View.VISIBLE }
        }
        c.close()

        btnthich.setOnClickListener {
            setLike(maso, 0)
            btnthich.visibility = View.INVISIBLE; btnkhongthich.visibility = View.VISIBLE
        }
        btnkhongthich.setOnClickListener {
            setLike(maso, 1)
            btnkhongthich.visibility = View.INVISIBLE; btnthich.visibility = View.VISIBLE
        }
    }

    private fun setLike(maso: String, value: Int) {
        val cv = ContentValues().apply { put("YEUTHICH", value) }
        MainActivity.database.update("ArirangSongList", cv, "MABH=?", arrayOf(maso))
    }
}