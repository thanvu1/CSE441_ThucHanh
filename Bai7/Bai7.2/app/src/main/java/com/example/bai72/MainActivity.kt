package com.example.bai72

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.Button
import android.widget.EditText

class MainActivity : AppCompatActivity() {
    private lateinit var edta: EditText
    private lateinit var edtb: EditText
    private lateinit var btnkq: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        edta = findViewById(R.id.edta)
        edtb = findViewById(R.id.edtb)
        btnkq = findViewById(R.id.btnkq)

        btnkq.setOnClickListener {
            val a = edta.text.toString().toIntOrNull() ?: 0
            val b = edtb.text.toString().toIntOrNull() ?: 0
            val bundle1 = Bundle()
            bundle1.putInt("soa", a)
            bundle1.putInt("sob", b)
            val myintent = Intent(this@MainActivity, KetQuaActivity::class.java)
            myintent.putExtra("mybackage", bundle1)
            startActivity(myintent)
        }
    }
}