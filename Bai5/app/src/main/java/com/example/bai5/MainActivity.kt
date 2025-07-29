package com.example.bai5

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView


class MainActivity : Activity() {
    private var editNamDuongLich: EditText? = null
    private var btnChuyenDoi: Button? = null
    private var btnChuyenGiaoDien: Button? = null
    private var txtKetQua: TextView? = null // id là textView5 trong layout bạn gửi

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editNamDuongLich = findViewById(R.id.editnamduonglich)
        btnChuyenDoi = findViewById(R.id.btnChuyenDoi)
        btnChuyenGiaoDien = findViewById(R.id.btnChuyenGiaoDien)
        txtKetQua = findViewById(R.id.textView5)

        btnChuyenDoi?.setOnClickListener {
            val strNam = editNamDuongLich?.text.toString().trim()
            if (strNam.isEmpty()) {
                txtKetQua?.text = "Vui lòng nhập năm!"
                return@setOnClickListener
            }
            val nam: Int = try {
                strNam.toInt()
            } catch (e: Exception) {
                txtKetQua?.text = "Năm không hợp lệ!"
                return@setOnClickListener
            }
            val can = arrayOf("Canh", "Tân", "Nhâm", "Quý", "Giáp", "Ất", "Bính", "Đinh", "Mậu", "Kỷ")
            val chi = arrayOf("Thân", "Dậu", "Tuất", "Hợi", "Tý", "Sửu", "Dần", "Mẹo", "Thìn", "Tỵ", "Ngọ", "Mùi")
            val namCan = can[nam % 10]
            val namChi = chi[nam % 12]
            txtKetQua?.text = "$namCan $namChi"
        }

        btnChuyenGiaoDien?.setOnClickListener {
            val intent = Intent(this, SecondActivity::class.java)
            // Đánh dấu chuyển từ MainActivity sang SecondActivity
            intent.putExtra("fromMain", true)
            startActivity(intent)
        }
    }
}
