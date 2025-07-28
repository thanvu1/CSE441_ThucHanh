package com.example.bai41

import android.app.Activity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class MainActivity : Activity() {
    private lateinit var edtdoC: EditText
    private lateinit var edtdoF: EditText
    private lateinit var btncf: Button
    private lateinit var btnfc: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        edtdoC = findViewById(R.id.txtCel)
        edtdoF = findViewById(R.id.txtFar)
        btncf = findViewById(R.id.btnCel)
        btnfc = findViewById(R.id.btnFar)
        val btnClear = findViewById<Button>(R.id.btnClear)

        btncf.setOnClickListener {
            val doC = edtdoC.text.toString()
            if (doC.isNotEmpty()) {
                val C = doC.toDouble()
                edtdoF.setText(String.format("%.2f", C * 1.8 + 32))
            }
        }

        btnfc.setOnClickListener {
            val doF = edtdoF.text.toString()
            if (doF.isNotEmpty()) {
                val F = doF.toDouble()
                edtdoC.setText(String.format("%.2f", (F - 32) / 1.8))
            }
        }

        btnClear.setOnClickListener {
            edtdoC.text.clear()
            edtdoF.text.clear()
        }
    }
}
