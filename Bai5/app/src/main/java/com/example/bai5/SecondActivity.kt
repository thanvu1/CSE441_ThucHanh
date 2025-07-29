package com.example.bai5

import android.app.Activity
import android.content.Intent
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
            val sa = edita?.text?.toString()?.trim()
            val sb = editb?.text?.toString()?.trim()
            val sc = editc?.text?.toString()?.trim()

            if (sa.isNullOrEmpty() || sb.isNullOrEmpty() || sc.isNullOrEmpty()) {
                txtkq?.text = getString(R.string.nhap_day_du_abc)
                return@setOnClickListener
            }

            try {
                val a = sa.toDouble()
                val b = sb.toDouble()
                val c = sc.toDouble()
                val dcf = DecimalFormat("0.00")

                val kq = if (a == 0.0) {
                    if (b == 0.0) {
                        if (c == 0.0) getString(R.string.vo_so_nghiem) else getString(R.string.vo_nghiem)
                    } else {
                        getString(R.string.mot_nghiem, dcf.format(-c / b))
                    }
                } else {
                    val delta = b * b - 4 * a * c
                    when {
                        delta < 0 -> getString(R.string.vo_nghiem)
                        delta == 0.0 -> getString(R.string.nghiem_kep, dcf.format(-b / (2 * a)))
                        else -> {
                            val x1 = (-b + sqrt(delta)) / (2 * a)
                            val x2 = (-b - sqrt(delta)) / (2 * a)
                            getString(R.string.hai_nghiem, dcf.format(x1), dcf.format(x2))
                        }
                    }
                }
                txtkq?.text = kq
            } catch (_: Exception) {
                txtkq?.text = getString(R.string.sai_dinh_dang)
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
            // Chuyển về MainActivity bằng Intent
            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
            startActivity(intent)
            finish()
        }
    }
}