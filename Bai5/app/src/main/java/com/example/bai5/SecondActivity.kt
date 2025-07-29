package com.example.bai5

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import java.text.DecimalFormat
import kotlin.math.sqrt

class SecondActivity : Activity() {
    private var btnTieptuc: Button? = null
    private var btnGiai: Button? = null
    private var btnThoat: Button? = null
    private var btnVechinh: Button? = null
    private var edita: EditText? = null
    private var editb: EditText? = null
    private var editc: EditText? = null
    private var txtkq: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
        btnTieptuc = findViewById(R.id.btntieptuc)
        btnGiai = findViewById(R.id.btngiaipt)
        btnThoat = findViewById(R.id.btnthoat)
        btnVechinh = findViewById(R.id.btnvechinh)
        edita = findViewById(R.id.edta)
        editb = findViewById(R.id.edtb)
        editc = findViewById(R.id.edtc)
        txtkq = findViewById(R.id.txtkq)

        btnGiai?.setOnClickListener {
            val sa = edita?.text.toString().trim()
            val sb = editb?.text.toString().trim()
            val sc = editc?.text.toString().trim()

            if (sa.isEmpty() || sb.isEmpty() || sc.isEmpty()) {
                txtkq?.text = "Vui lòng nhập đủ a, b, c"
                return@setOnClickListener
            }

            try {
                val a = sa.toDouble()
                val b = sb.toDouble()
                val c = sc.toDouble()
                var kq = ""
                val dcf = DecimalFormat("0.00")

                if (a == 0.0) {
                    if (b == 0.0) {
                        kq = if (c == 0.0) "PT vô số nghiệm" else "PT vô nghiệm"
                    } else {
                        kq = "PT có 1 nghiệm, x = ${dcf.format(-c / b)}"
                    }
                } else {
                    val delta = b * b - 4 * a * c
                    kq = when {
                        delta < 0 -> "PT vô nghiệm"
                        delta == 0.0 -> "PT có nghiệm kép x1 = x2 = ${dcf.format(-b / (2 * a))}"
                        else -> {
                            val x1 = (-b + sqrt(delta)) / (2 * a)
                            val x2 = (-b - sqrt(delta)) / (2 * a)
                            "PT có 2 nghiệm: x1 = ${dcf.format(x1)}; x2 = ${dcf.format(x2)}"
                        }
                    }
                }
                txtkq?.text = kq
            } catch (ex: Exception) {
                txtkq?.text = "Sai định dạng dữ liệu!"
            }
        }

        btnTieptuc?.setOnClickListener {
            edita?.setText("")
            editb?.setText("")
            editc?.setText("")
            txtkq?.text = ""
            edita?.requestFocus()
        }

        btnThoat?.setOnClickListener {
            finish()
        }

        btnVechinh?.setOnClickListener {
            finish()
        }
    }
}