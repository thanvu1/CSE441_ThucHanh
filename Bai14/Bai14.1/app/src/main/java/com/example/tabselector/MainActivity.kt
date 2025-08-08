package com.example.tabselector

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var edta: EditText
    private lateinit var edtb: EditText
    private lateinit var btncong: Button
    private lateinit var lv1: ListView
    private lateinit var list: ArrayList<String>
    private lateinit var myarray: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        addControl()
        addEvent()
    }

    private fun addControl() {
        val tab = findViewById<TabHost>(R.id.tabhost)
        tab.setup()

        // Tab 1
        val tab1 = tab.newTabSpec("t1")
        tab1.setContent(R.id.tab1) // <-- phải đúng ID của LinearLayout tab1
        tab1.setIndicator("Phép cộng") // icon cộng
        tab.addTab(tab1)

        // Tab 2
        val tab2 = tab.newTabSpec("t2")
        tab2.setContent(R.id.tab2) // <-- phải đúng ID của LinearLayout tab2
        tab2.setIndicator("Lịch sử") // icon lịch sử
        tab.addTab(tab2)

        edta = findViewById(R.id.edta)
        edtb = findViewById(R.id.edtb)
        btncong = findViewById(R.id.btncong)
        lv1 = findViewById(R.id.lv1)
        list = ArrayList()
        myarray = ArrayAdapter(this, android.R.layout.simple_list_item_1, list)
        lv1.adapter = myarray
    }

    private fun addEvent() {
        btncong.setOnClickListener {
            xulycong()
        }
    }

    private fun xulycong() {
        val a = edta.text.toString().toIntOrNull() ?: 0
        val b = edtb.text.toString().toIntOrNull() ?: 0
        val c = "$a + $b = ${a + b}"
        list.add(c)
        myarray.notifyDataSetChanged()
        edta.setText("")
        edtb.setText("")
    }
}