package com.example.bai71

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class ChildActivity : AppCompatActivity() {
    protected override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_child)
        val btn = findViewById(R.id.button1) as Button
        val txt1 = findViewById(R.id.textView1) as TextView?
        btn.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                val intent1: Intent = Intent(this@ChildActivity, MainActivity::class.java)
                startActivity(intent1)
            }
        })
    }
}