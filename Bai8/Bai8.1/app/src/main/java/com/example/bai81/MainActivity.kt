package com.example.bai81

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.activity_main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val btnCall = findViewById<Button>(R.id.btncallphone)
        val btnSend = findViewById<Button>(R.id.btnsendsms)
        btnCall.setOnClickListener {
            val intent1 = Intent(this@MainActivity, CallPhoneActivity::class.java)
            startActivity(intent1)
        }
        btnSend.setOnClickListener {
            val intent2 = Intent(this@MainActivity, SendSMSActivity::class.java)
            startActivity(intent2)
        }
    }

}