package com.example.spinner

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.widget.*

class MainActivity : Activity() {
    private val arr1 = arrayOf(
        "Hàng Điện tử", "Hàng Hóa Chất",
        "Hàng Gia dụng", "Hàng xây dựng"
    )

    private lateinit var txt1: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        txt1 = findViewById(R.id.txt1)
        val spin1 = findViewById<Spinner>(R.id.spinner1)

        val adapter1 = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            arr1
        )
        adapter1.setDropDownViewResource(android.R.layout.simple_list_item_single_choice)
        spin1.adapter = adapter1

        spin1.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                txt1.text = arr1[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Không làm gì cả
            }
        }
    }
}
