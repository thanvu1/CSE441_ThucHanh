package com.example.customlistview

import android.content.Intent
import android.os.Bundle
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.example.customlistview.adapter.MyArrayAdapter
import com.example.customlistview.model.Phone

class MainActivity : AppCompatActivity() {
    private lateinit var phoneList: ArrayList<Phone>
    private lateinit var adapter: MyArrayAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val lv = findViewById<ListView>(R.id.listView)

        // Thay bằng link ảnh thật trên internet
        val names = arrayOf(
            "Điện thoại Iphone 12",
            "Điện thoại SamSung S20",
            "Điện thoại Nokia 6",
            "Điện thoại Bphone 2020",
            "Điện thoại Oppo 5",
            "Điện thoại VSmart joy2"
        )
        val images = arrayOf(
            "https://cdn.tgdd.vn/Products/Images/42/213031/iphone-12-xanh-duong-1-600x600.jpg",
            "https://cdn.tgdd.vn/Products/Images/42/223602/samsung-galaxy-s20-600x600-600x600.jpg",
            "https://cdn.tgdd.vn/Products/Images/42/223175/nokia-6.1-plus-1-600x600.jpg",
            "https://cdn.tgdd.vn/Products/Images/42/215432/bphone-2020-600x600-1-600x600.jpg",
            "https://cdn.tgdd.vn/Products/Images/42/229053/oppo-a95-xanh-thumb-1-600x600.jpg",
            "https://cdn.tgdd.vn/Products/Images/42/218287/vsmart-joy-2-plus-hong-600x600.jpg"
        )

        phoneList = ArrayList()
        for (i in names.indices) {
            phoneList.add(Phone(names[i], images[i]))
        }
        adapter = MyArrayAdapter(this, R.layout.layout_listview, phoneList)
        lv.adapter = adapter

        lv.setOnItemClickListener { _, _, position, _ ->
            val intent = Intent(this, SubActivity::class.java)
            intent.putExtra("name", names[position])
            startActivity(intent)
        }
    }
}
