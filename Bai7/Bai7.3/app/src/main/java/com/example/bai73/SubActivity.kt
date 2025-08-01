package com.example.bai73

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity

class SubActivity : AppCompatActivity() {
    private lateinit var edtAA: EditText
    private lateinit var edtBB: EditText
    private lateinit var btnsendtong: Button
    private lateinit var btnsendhieu: Button
    private lateinit var myintent: Intent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sub)
        edtAA = findViewById(R.id.edtAA)
        edtBB = findViewById(R.id.edtBB)
        btnsendtong = findViewById(R.id.btnsendtong)
        btnsendhieu = findViewById(R.id.btnsendhieu)
        // Nhận Intent
        myintent = intent
        // lấy dữ liệu khỏi Intent
        val a = myintent.getIntExtra("soa", 0)
        val b = myintent.getIntExtra("sob", 0)
        edtAA.setText(a.toString())
        edtBB.setText(b.toString())
        btnsendtong.setOnClickListener {
            //Xử lý kết quả
            val sum = a + b
            // Đẩy kết quả trở lại Intent
            myintent.putExtra("kq", sum)
            // Trả intent trở về
            setResult(33, myintent)
            //thoát Activity này để quay về
            finish()
        }
        btnsendhieu.setOnClickListener {
            //Xử lý kết quả
            val sub = a - b
            myintent.putExtra("kq", sub)
            setResult(34, myintent)
            finish()
        }
    }
}