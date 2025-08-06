package com.example.phonelist

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView


class MainActivity : Activity() {
    var txt1: TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        txt1 = findViewById<TextView?>(R.id.selection)

//1. Khởi tạo dữ liệu cho mảng arr (còn gọi là data source)
        val arr1: Array<String> = arrayOf(
            "Iphone 7", "SamSung Galaxy S7",
            "Nokia Lumia 730", "Sony Xperia XZ", "HTC One E9"
        )

//2. Khai báo Adapter và gán Data source vào ArrayAdapter
        val adapter1: ArrayAdapter<String> =
            ArrayAdapter<String>(this, R.layout.simple_list_item, arr1)

//3. Khai báo Listview và đưa Adapter  vào ListView
        val lv1 = findViewById<ListView?>(R.id.lv1)
        lv1?.setAdapter(adapter1)

//4. Viết sự kiện khi Click vào một dòng trên ListView
        lv1?.setOnItemClickListener(object : OnItemClickListener {
            override fun onItemClick(
                arg0: AdapterView<*>?, arg1: View?, i: Int,
                arg3: Long
            ) {
                txt1?.setText(getString(R.string.item_position, i + 1, arr1[i]))
            }
        })
    }
}