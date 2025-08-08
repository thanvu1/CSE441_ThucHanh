package com.example.customlayoutver3

import android.os.Bundle
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.example.customlayoutver3.adpater.MyArrayAdapter
import com.example.customlayoutver3.model.Contact

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val listView = findViewById<ListView>(R.id.listView)

        // Thay link icon thành link thật (PNG, SVG được hỗ trợ tốt)
        val contacts = listOf(
            Contact(
                "Nguyễn Văn Nam", "0932588634",
                "https://cdn-icons-png.flaticon.com/512/455/455705.png", // call
                "https://cdn-icons-png.flaticon.com/512/684/684908.png", // sms
                "https://cdn-icons-png.flaticon.com/512/1250/1250615.png" // contact
            ),
            Contact(
                "Trần Văn Tú", "0932588635",
                "https://cdn-icons-png.flaticon.com/512/455/455705.png",
                "https://cdn-icons-png.flaticon.com/512/684/684908.png",
                "https://cdn-icons-png.flaticon.com/512/1250/1250615.png"
            ),
            Contact(
                "Nguyễn Thị Lan", "0932588636",
                "https://cdn-icons-png.flaticon.com/512/455/455705.png",
                "https://cdn-icons-png.flaticon.com/512/684/684908.png",
                "https://cdn-icons-png.flaticon.com/512/1250/1250615.png"
            ),
            Contact(
                "Nguyễn Kim Ngân", "0932588637",
                "https://cdn-icons-png.flaticon.com/512/455/455705.png",
                "https://cdn-icons-png.flaticon.com/512/684/684908.png",
                "https://cdn-icons-png.flaticon.com/512/1250/1250615.png"
            ),
        )

        val adapter = MyArrayAdapter(this, R.layout.layout_listitem, contacts)
        listView.adapter = adapter
    }
}
