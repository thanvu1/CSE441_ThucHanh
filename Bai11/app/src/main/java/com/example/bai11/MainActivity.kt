package com.example.bai11

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    var btnopen: Button? = null
    var edtlink: EditText? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        edtlink = findViewById<EditText?>(R.id.edtlink)
        btnopen = findViewById<Button?>(R.id.btnopen)
        btnopen!!.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                // TODO Auto-generated method stub
                val intent = Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://" + edtlink!!.getText().toString())
                )
                startActivity(intent)
            }
        })
    }
}