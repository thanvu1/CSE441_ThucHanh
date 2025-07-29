package com.example.bai4_2

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import java.text.DecimalFormat

class MainActivity : AppCompatActivity() {
    private lateinit var btnChandoan: Button
    private lateinit var editTen: EditText
    private lateinit var editChieucao: EditText
    private lateinit var editCannang: EditText
    private lateinit var editBMI: EditText
    private lateinit var editChandoan: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btnChandoan = findViewById(R.id.btnBMI)
        editTen = findViewById(R.id.edtten)
        editChieucao = findViewById(R.id.edtchieucao)
        editCannang = findViewById(R.id.edtcannang)
        editBMI = findViewById(R.id.edtBMI)
        editChandoan = findViewById(R.id.edtChuanDoan)

        btnChandoan.setOnClickListener {
            val H_cm = editChieucao.text.toString().toDoubleOrNull() ?: 0.0
            val H = H_cm / 100 // Đổi sang mét!
            val W = editCannang.text.toString().toDoubleOrNull() ?: 0.0
            val BMI = if (H > 0) W / (H * H) else 0.0
            val chandoan = when {
                BMI < 18 -> "Bạn gầy"
                BMI <= 24.9 -> "Bạn bình thường"
                BMI <= 29.9 -> "Bạn béo phì độ 1"
                BMI <= 34.9 -> "Bạn béo phì độ 2"
                else -> "Bạn béo phì độ 3"
            }
            val dcf = DecimalFormat("#.0")
            editBMI.setText(dcf.format(BMI))
            editChandoan.setText(chandoan)
        }
    }
}