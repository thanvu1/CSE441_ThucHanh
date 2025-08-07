package com.example.imggridview

import android.content.Intent
import android.os.Bundle
import android.widget.GridView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.imggridview.adapter.ImageAdapter

class MainActivity : AppCompatActivity() {
    private val images = listOf(
        "https://images.unsplash.com/photo-1506744038136-46273834b3fb",
        "https://images.unsplash.com/photo-1465101046530-73398c7f28ca",
        "https://images.unsplash.com/photo-1465101178521-c1a9136a3b8a",
        "https://images.unsplash.com/photo-1500534314209-a25ddb2bd429",
        "https://images.unsplash.com/photo-1465101046530-73398c7f28ca"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val gridView = findViewById<GridView>(R.id.gridView)
        gridView.adapter = ImageAdapter(this, images)
        gridView.setOnItemClickListener { _, _, position, _ ->
            val intent = Intent(this, ImageActivity::class.java)
            intent.putExtra("image_url", images[position])
            startActivity(intent)
        }
    }
}
