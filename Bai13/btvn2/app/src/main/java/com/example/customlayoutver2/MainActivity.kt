package com.example.customlayoutver2

import android.content.Intent
import android.os.Bundle
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.example.customlayoutver2.adapter.MyArrayAdapter
import com.example.customlayoutver2.model.Phone

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val listView = findViewById<ListView>(R.id.listView)

        // Danh sách điện thoại + ảnh online
        val phones = listOf(
            Phone("Điện thoại Sky", 5000000, "https://cdn.tgdd.vn/Products/Images/42/153856/TimerThumb/iphone-12-(2).jpg"),
            Phone("Điện thoại SamSung", 10000000, "https://cdn.tgdd.vn/Products/Images/42/213031/TimerThumb/samsung-galaxy-s20.jpg"),
            Phone("Điện thoại IP", 20000000, "https://cdn.tgdd.vn/Products/Images/42/153856/iphone-12.jpg"),
            Phone("Điện thoại HTC", 8000000, "https://cdn.tgdd.vn/Products/Images/42/167460/htc.jpg"),
            Phone("Điện thoại LG", 8500000, "https://cdn.tgdd.vn/Products/Images/42/167462/lg.jpg"),
            Phone("Điện thoại WP", 6000000, "https://cdn.tgdd.vn/Products/Images/42/167464/wp.jpg"),
        )

        val adapter = MyArrayAdapter(this, R.layout.custom_listview_item, phones)
        listView.adapter = adapter

        listView.setOnItemClickListener { _, _, position, _ ->
            val phone = phones[position]
            val intent = Intent(this, SubActivity::class.java)
            intent.putExtra("name", phone.name)
            intent.putExtra("price", phone.price)
            intent.putExtra("img", phone.imageUrl)
            startActivity(intent)
        }
    }
}
