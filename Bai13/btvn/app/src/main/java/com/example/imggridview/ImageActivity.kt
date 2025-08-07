package com.example.imggridview

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Picasso

class ImageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image)
        val imgLarge = findViewById<ImageView>(R.id.imgLarge)
        val btnBack = findViewById<Button>(R.id.btnBack)
        val imageUrl = intent.getStringExtra("image_url")
        if (imageUrl != null) {
            Picasso.get()
                .load(imageUrl)
                .placeholder(android.R.drawable.ic_menu_gallery)
                .error(android.R.drawable.ic_delete)
                .into(imgLarge)
        }
        btnBack.setOnClickListener { finish() }
    }
}
