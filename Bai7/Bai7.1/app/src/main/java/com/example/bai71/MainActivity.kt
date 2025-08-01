package com.example.bai71

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.bai71.ChildActivity

class MainActivity : AppCompatActivity() {
    protected override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val btn1 = findViewById(R.id.button1) as Button
        btn1.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                val intent1: Intent = Intent(this@MainActivity, ChildActivity::class.java)
                startActivity(intent1)
            }
        })
    }
}