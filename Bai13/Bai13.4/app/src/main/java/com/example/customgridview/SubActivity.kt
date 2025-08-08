package com.example.customgridview

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Picasso

class SubActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_child)

        val txtName = findViewById<TextView>(R.id.textView2)
        val img = findViewById<ImageView>(R.id.imageView2)
        val name = intent.getStringExtra("name")
        val url = intent.getStringExtra("url")
        txtName.text = "Bạn đã chọn " + name
        Picasso.get().load(url).into(img)
    }
}
