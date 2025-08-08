package com.example.customgridview

import android.content.Intent
import android.os.Bundle
import android.widget.GridView
import androidx.appcompat.app.AppCompatActivity
import com.example.customgridview.adapter.MyArrayAdapter
import com.example.customgridview.model.Image

class MainActivity : AppCompatActivity() {

    // 12 URL ảnh demo
    private val urlImages = listOf(
        "https://img.tripi.vn/cdn-cgi/image/width=700,height=700/https://gcs.tripi.vn/public-tripi/tripi-feed/img/482812ZYV/anh-mo-ta.png",
        "https://img.tripi.vn/cdn-cgi/image/width=700,height=700/https://gcs.tripi.vn/public-tripi/tripi-feed/img/482812ZYV/anh-mo-ta.png",
        "https://d1hjkbq40fs2x4.cloudfront.net/2017-08-21/files/landscape-photography_1645.jpg",
        "https://hoanghamobile.com/tin-tuc/wp-content/uploads/2024/10/tai-anh-phong-canh-dep-45.jpg",
        "https://i.pinimg.com/736x/91/36/9d/91369d9fbf87b5b8f3e495959ccc11e8.jpg",
        "https://halotravel.vn/wp-content/uploads/2021/09/anh-phong-canh-dep-15.jpg",
        "https://img.tripi.vn/cdn-cgi/image/width=700,height=700/https://gcs.tripi.vn/public-tripi/tripi-feed/img/482812ZYV/anh-mo-ta.png",
        "https://img.tripi.vn/cdn-cgi/image/width=700,height=700/https://gcs.tripi.vn/public-tripi/tripi-feed/img/482812ZYV/anh-mo-ta.png",
        "https://d1hjkbq40fs2x4.cloudfront.net/2017-08-21/files/landscape-photography_1645.jpg",
        "https://hoanghamobile.com/tin-tuc/wp-content/uploads/2024/10/tai-anh-phong-canh-dep-45.jpg",
        "https://i.pinimg.com/736x/91/36/9d/91369d9fbf87b5b8f3e495959ccc11e8.jpg",
        "https://halotravel.vn/wp-content/uploads/2021/09/anh-phong-canh-dep-15.jpg"
    )
    private val names = List(12) { "Ảnh ${it + 1}" }
    private val arrimage = List(12) { i -> Image(urlImages[i], names[i]) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val gridView = findViewById<GridView>(R.id.grid1)
        val adapter = MyArrayAdapter(this, R.layout.list_item, arrimage)
        gridView.adapter = adapter

        gridView.setOnItemClickListener { _, _, position, _ ->
            val intent = Intent(this, SubActivity::class.java)
            intent.putExtra("name", arrimage[position].name)
            intent.putExtra("url", arrimage[position].url)
            startActivity(intent)
        }
    }
}
