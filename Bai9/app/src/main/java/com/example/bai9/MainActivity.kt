package com.example.bai9

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Picasso


class MainActivity : AppCompatActivity() {
    var btnplay: ImageButton? = null
    var btnstop: ImageButton? = null
    var flag: Boolean = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btnplay = findViewById<ImageButton?>(R.id.imagstart)
        btnstop = findViewById<ImageButton?>(R.id.imgstop)
        val imageView1 = findViewById<ImageView>(R.id.imageView1)
        Picasso.get().load("https://static.vecteezy.com/system/resources/thumbnails/031/617/767/small_2x/abstract-music-background-with-notes-and-bokeh-lights-illustration-music-icon-song-time-wavy-shape-generative-ai-photo.jpg").into(imageView1)
        Picasso.get().load("https://cdn-icons-png.flaticon.com/512/3318/3318660.png").into(btnplay) // icon play
        Picasso.get().load("https://cdn-icons-png.flaticon.com/512/727/727243.png").into(btnstop)

        // Xử lý click
        btnplay!!.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {
                val intent1 = Intent(this@MainActivity, MyService::class.java)
                startService(intent1)
                if (flag == true) {
                    Picasso.get().load("https://www.svgrepo.com/show/390471/media-music-pause-play-song-stop.svg").into(btnplay)
                    flag = false
                } else {
                    Picasso.get().load("https://svgsilh.com/svg/468294.svg").into(btnplay)
                    flag = true
                }
            }
        })
        btnstop!!.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {
                val intent2 = Intent(this@MainActivity, MyService::class.java)
                stopService(intent2)
                Picasso.get().load("https://cdn-icons-png.flaticon.com/512/3318/3318660.png").into(btnplay)
                flag = true
            }
        })
    }
}