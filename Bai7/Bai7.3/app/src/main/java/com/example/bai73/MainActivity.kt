package com.example.bai73

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

public class MainActivity : AppCompatActivity() {
    lateinit var edtA: EditText
    lateinit var edtB: EditText
    lateinit var edtKQ: EditText
    lateinit var btnrequest: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        edtA = findViewById(R.id.edtA)
        edtB = findViewById(R.id.edtB)
        edtKQ = findViewById(R.id.edtkq)
        btnrequest = findViewById(R.id.btnrequest)
        //--------------------------------------
        btnrequest.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {
                //Khai báo intent
                val myintent = Intent(this@MainActivity, SubActivity::class.java)
                //Lấy dữ liệu a, b
                val a = Integer.parseInt(edtA.text.toString())
                val b = Integer.parseInt(edtB.text.toString())
                // Đẩy dữ liệu vào Intent
                myintent.putExtra("soa", a)
                myintent.putExtra("sob", b)
                // Khởi động Intent và chờ kết quả trả về
                startActivityForResult(myintent, 99)
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 99 && resultCode == 33) {
            val sum = data?.getIntExtra("kq", 0) ?: 0
            edtKQ.setText("Tổng 2 số là: $sum")
        }
        if (requestCode == 99 && resultCode == 34) {
            val sub = data?.getIntExtra("kq", 0) ?: 0
            edtKQ.setText("Hiệu 2 số là: $sub")
        }
    }
}
