package com.example.customlayoutver2

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView
import com.squareup.picasso.Picasso

class SubActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sub)

        val txtName = findViewById<TextView>(R.id.txtName)
        val txtPrice = findViewById<TextView>(R.id.txtPrice)
        val imgPhone = findViewById<ImageView>(R.id.imgPhone)

        val name = intent.getStringExtra("name")
        val price = intent.getIntExtra("price", 0)
        val img = intent.getStringExtra("img")

        txtName.text = name
        txtPrice.text = "Giá bán: ${String.format("%,d", price)}"
        Picasso.get().load(img).into(imgPhone)
    }

}