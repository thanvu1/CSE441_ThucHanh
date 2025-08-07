package com.example.gridview

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.GridView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private val arr = arrayOf(
        "Ipad", "Iphone", "New Ipad",
        "SamSung", "Nokia", "Sony Ericson",
        "LG", "Q-Mobile", "HTC", "Blackberry",
        "G Phone", "FPT - Phone", "HK Phone"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val selection = findViewById<TextView>(R.id.selection)
        val gv = findViewById<GridView>(R.id.gridView1)

        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            arr
        )
        gv.adapter = adapter

        gv.setOnItemClickListener { _, _, position, _ ->
            selection.text = arr[position]
        }
    }
}