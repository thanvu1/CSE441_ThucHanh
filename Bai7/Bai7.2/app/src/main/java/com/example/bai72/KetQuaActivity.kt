package com.example.bai72

import android.icu.text.DecimalFormat
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent


class KetQuaActivity : AppCompatActivity() {
    var edtkq: TextView? = null
    var btnback: Button? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ketqua)
        edtkq = findViewById<EditText?>(R.id.txtketqua)
        btnback = findViewById<Button?>(R.id.btnback)
        val yourintent = getIntent()
        val yourbundle = yourintent.getBundleExtra("mybackage")
        val a = yourbundle!!.getInt("soa")
        val b = yourbundle.getInt("sob")
        var kq: String? = ""
        if (a == 0 && b == 0) {
            kq = "Vô số nghiệm"
        } else if (a == 0 && b != 0) {
            kq = "Vô nghiệm"
        } else {
            val dcf: DecimalFormat = DecimalFormat("0.##")
            kq = dcf.format(-b * 1.0 / a)
        }
        edtkq!!.setText(kq)
        btnback!!.setOnClickListener {
            val intent = Intent(this@KetQuaActivity, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
            finish()
        }
    }
}