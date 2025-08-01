package com.example.bai81

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.squareup.picasso.Picasso


class CallPhoneActivity : AppCompatActivity() {
    var edtcall: EditText? = null
    var btncallphone: ImageButton? = null
    var btnback1: Button? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_call_phone)
        edtcall = findViewById<View?>(R.id.edtcall) as EditText
        btnback1 = findViewById<View?>(R.id.btnback1) as Button
        btncallphone = findViewById<View?>(R.id.btncall) as ImageButton
        // Sử dụng Picasso để thay ảnh cho button
        Picasso.get().load("https://cdn-icons-png.flaticon.com/512/9073/9073336.png").into(btncallphone)
        btncallphone!!.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {
                val callintent = Intent(
                    Intent.ACTION_CALL,
                    Uri.parse("tel:" + edtcall!!.getText().toString())
                )

// Yêu cầu người dùng đồng ý quyền truy cập vào tính năng gọi điện
                if (ActivityCompat.checkSelfPermission(
                        this@CallPhoneActivity,
                        Manifest.permission.CALL_PHONE
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    ActivityCompat.requestPermissions(
                        this@CallPhoneActivity, arrayOf(
                            Manifest.permission.CALL_PHONE
                        ), 1
                    )
                } else {
                    startActivity(callintent)
                }
            }
        })
        btnback1!!.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {
                finish()
            }
        })
    }
}