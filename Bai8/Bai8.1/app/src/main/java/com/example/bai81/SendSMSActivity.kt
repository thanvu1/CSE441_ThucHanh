package com.example.bai81

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import com.squareup.picasso.Picasso
import androidx.appcompat.app.AppCompatActivity


class SendSMSActivity : AppCompatActivity() {
    var edtsms: EditText? = null
    var btnback2: Button? = null
    var btnsms: ImageButton? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_send_sms)
        edtsms = findViewById<EditText>(R.id.edtsms)
        btnback2 = findViewById<Button>(R.id.btnback2)
        btnsms = findViewById<ImageButton>(R.id.btnsms)
        // Load icon into ImageButton using Picasso
        Picasso.get().load("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTc4sj8S144yhF4X39Be_d74Mtln_Yli7_IGg&s").into(btnsms)
        btnsms!!.setOnClickListener {
            val callintent = Intent(
                Intent.ACTION_SENDTO,
                Uri.parse("smsto:" + edtsms!!.text.toString())
            )
            startActivity(callintent)
        }
        btnback2!!.setOnClickListener {
            finish()
        }
    }
}