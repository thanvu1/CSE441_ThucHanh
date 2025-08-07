package com.example.autocompletetextview

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var selection: TextView
    private lateinit var singleComplete: AutoCompleteTextView
    private lateinit var multiComplete: MultiAutoCompleteTextView

    private val arr = arrayOf(
        "hà nội", "Huế", "Sài gòn",
        "hà giang", "Hội an", "Kiên giang",
        "Lâm đồng", "Long khánh"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        selection = findViewById(R.id.selection)
        singleComplete = findViewById(R.id.editauto)
        multiComplete = findViewById(R.id.multiAutoCompleteTextView1)

        // Adapter chung cho cả hai
        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            arr
        )
        singleComplete.setAdapter(adapter)
        multiComplete.setAdapter(adapter)
        multiComplete.setTokenizer(MultiAutoCompleteTextView.CommaTokenizer())

        // Cập nhật TextView selection khi text thay đổi trong singleComplete
        singleComplete.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                selection.text = singleComplete.text
            }
            override fun afterTextChanged(s: Editable?) { }
        })
    }
}
