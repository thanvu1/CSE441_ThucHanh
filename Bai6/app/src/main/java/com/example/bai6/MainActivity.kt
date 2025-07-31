package com.example.bai6

import android.app.AlertDialog
import android.os.Bundle
import android.widget.*
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Picasso

class MainActivity : AppCompatActivity() {
    private lateinit var edtten: EditText
    private lateinit var editCMND: EditText
    private lateinit var editBosung: EditText
    private lateinit var chkdocbao: CheckBox
    private lateinit var chkdocsach: CheckBox
    private lateinit var chkdoccode: CheckBox
    private lateinit var btnsend: Button
    private lateinit var group: RadioGroup
    private lateinit var imgAvatar: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        edtten = findViewById(R.id.edtten)
        editCMND = findViewById(R.id.edtcmnd)
        editBosung = findViewById(R.id.edtbosung)
        chkdocbao = findViewById(R.id.chkdocbao)
        chkdocsach = findViewById(R.id.chkdocsach)
        chkdoccode = findViewById(R.id.chkcode)
        btnsend = findViewById(R.id.btnsend)
        group = findViewById(R.id.idgruop)
        imgAvatar = findViewById(R.id.imgAvatar)

        // Load avatar bằng Picasso
        val avatarUrl = "https://i.imgur.com/4rqDe4J.jpeg"
        Picasso.get()
            .load(avatarUrl)
            .placeholder(R.drawable.ic_launcher_foreground)
            .error(R.drawable.ic_launcher_foreground)
            .into(imgAvatar)

        btnsend.setOnClickListener {
            doShowInformation()
        }

        // **XỬ LÝ BACK BẰNG CALLBACK CHUẨN ANDROIDX**
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                AlertDialog.Builder(this@MainActivity)
                    .setTitle("Question")
                    .setMessage("Are you sure you want to exit?")
                    .setIcon(R.drawable.ic_launcher_foreground)
                    .setPositiveButton("YES") { _, _ -> finish() }
                    .setNegativeButton("NO") { dialog, _ -> dialog.dismiss() }
                    .show()
            }
        })
    }

    private fun doShowInformation() {
        val ten = edtten.text.toString().trim()
        if (ten.length < 3) {
            edtten.requestFocus()
            edtten.selectAll()
            Toast.makeText(this, "Tên phải >= 3 ký tự", Toast.LENGTH_LONG).show()
            return
        }
        val cmnd = editCMND.text.toString().trim()
        if (cmnd.length != 9) {
            editCMND.requestFocus()
            editCMND.selectAll()
            Toast.makeText(this, "CMND phải đúng 9 ký tự", Toast.LENGTH_LONG).show()
            return
        }
        val id = group.checkedRadioButtonId
        if (id == -1) {
            Toast.makeText(this, "Phải chọn bằng cấp", Toast.LENGTH_LONG).show()
            return
        }
        val rad = findViewById<RadioButton>(id)
        val bang = rad.text.toString()
        var sothich = ""
        if (chkdocbao.isChecked) sothich += chkdocbao.text.toString() + "- "
        if (chkdocsach.isChecked) sothich += chkdocsach.text.toString() + "- "
        if (chkdoccode.isChecked) sothich += chkdoccode.text.toString()
        sothich = sothich.trimEnd('-', ' ')
        val bosung = editBosung.text.toString()

        val msg = """
            $ten
            $cmnd
            $bang
            $sothich
            ----------------------
            Thông tin bổ sung:
            $bosung
            ----------------------
        """.trimIndent()

        AlertDialog.Builder(this)
            .setTitle("Thông tin cá nhân")
            .setMessage(msg)
            .setPositiveButton("Đóng") { dialog, _ -> dialog.dismiss() }
            .create()
            .show()
    }
}
