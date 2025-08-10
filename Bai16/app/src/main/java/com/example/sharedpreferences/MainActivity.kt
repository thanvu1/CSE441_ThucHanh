package com.example.sharedpreferences

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var edta: EditText
    private lateinit var edtb: EditText
    private lateinit var edtkq: EditText
    private lateinit var btntong: Button
    private lateinit var btnclear: Button
    private lateinit var txtlichsu: TextView

    private var lichsu: String = ""
    private val PREF_NAME = "mysave"
    private val KEY_HISTORY = "ls"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Bind view
        edta = findViewById(R.id.edta)
        edtb = findViewById(R.id.edtb)
        edtkq = findViewById(R.id.edtkq)
        btntong = findViewById(R.id.btntong)
        btnclear = findViewById(R.id.btnclear)
        txtlichsu = findViewById(R.id.txtlichsu)
        txtlichsu.movementMethod = android.text.method.ScrollingMovementMethod.getInstance()

        // Lấy lại lịch sử đã lưu
        val prefs = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        lichsu = prefs.getString(KEY_HISTORY, "") ?: ""
        txtlichsu.text = lichsu

        // Tính tổng
        btntong.setOnClickListener {
            val a = edta.text.toString().trim().toIntOrNull()
            val b = edtb.text.toString().trim().toIntOrNull()

            if (a == null || b == null) {
                Toast.makeText(this, "Vui lòng nhập A/B là số nguyên", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val kq = a + b
            edtkq.setText(kq.toString())

            // Cập nhật lịch sử (mỗi phép tính một dòng)
            // Thêm xuống dòng trước nếu lịch sử đang có nội dung
            if (lichsu.isNotEmpty()) lichsu += "\n"
            lichsu += "$a + $b = $kq"
            txtlichsu.text = lichsu

            // (tuỳ chọn) tự scroll xuống cuối
            txtlichsu.post {
                val layout = txtlichsu.layout ?: return@post
                val scrollAmount = layout.getLineTop(txtlichsu.lineCount) - txtlichsu.height
                if (scrollAmount > 0) txtlichsu.scrollTo(0, scrollAmount) else txtlichsu.scrollTo(0, 0)
            }
        }

        // Xoá lịch sử
        btnclear.setOnClickListener {
            lichsu = ""
            txtlichsu.text = lichsu
            Toast.makeText(this, "Đã xoá lịch sử", Toast.LENGTH_SHORT).show()
        }
    }

    // Lưu lịch sử khi app rời foreground (đúng yêu cầu đề)
    override fun onPause() {
        super.onPause()
        val prefs = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        prefs.edit().putString(KEY_HISTORY, lichsu).apply()
    }
}