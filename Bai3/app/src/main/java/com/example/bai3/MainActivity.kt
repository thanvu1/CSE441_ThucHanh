package com.example.bai3

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var edt1: EditText
    private lateinit var edt2: EditText
    private lateinit var edt3: EditText
    private lateinit var btncong: Button
    private lateinit var btntru: Button
    private lateinit var btnnhan: Button
    private lateinit var btnchia: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        edt1 = findViewById(R.id.edta)
        edt2 = findViewById(R.id.edtb)
        edt3 = findViewById(R.id.edtc)
        btncong = findViewById(R.id.btncong)
        btntru = findViewById(R.id.btntru)
        btnnhan = findViewById(R.id.btnnhan)
        btnchia = findViewById(R.id.btnchia)

        btncong.setOnClickListener {
            val a = edt1.text.toString().toIntOrNull() ?: 0
            val b = edt2.text.toString().toIntOrNull() ?: 0
            edt3.setText((a + b).toString())
        }
        btntru.setOnClickListener {
            val a = edt1.text.toString().toIntOrNull() ?: 0
            val b = edt2.text.toString().toIntOrNull() ?: 0
            edt3.setText((a - b).toString())
        }
        btnnhan.setOnClickListener {
            val a = edt1.text.toString().toIntOrNull() ?: 0
            val b = edt2.text.toString().toIntOrNull() ?: 0
            edt3.setText((a * b).toString())
        }
        btnchia.setOnClickListener {
            val a = edt1.text.toString().toIntOrNull() ?: 0
            val b = edt2.text.toString().toIntOrNull() ?: 0
            if (b == 0) {
                edt3.setText("B phải khác 0")
            } else {
                edt3.setText((a / b).toString())
            }
        }
    }
}