package com.example.note

import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.*
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : Activity() {
    private lateinit var lv: ListView
    private lateinit var arraywork: ArrayList<String>
    private lateinit var arrAdapter: ArrayAdapter<String>
    private lateinit var edtwork: EditText
    private lateinit var edth: EditText
    private lateinit var edtm: EditText
    private lateinit var txtdate: TextView
    private lateinit var btnwork: Button

    companion object {
        const val PREFS_NAME = "WORKS_PREFS"
        const val WORKS_KEY = "works_list"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Liên kết view
        edth = findViewById(R.id.edthour)
        edtm = findViewById(R.id.edtmi)
        edtwork = findViewById(R.id.edtwork)
        btnwork = findViewById(R.id.btnadd)
        lv = findViewById(R.id.listView1)
        txtdate = findViewById(R.id.txtdate)

        // Khởi tạo danh sách & load dữ liệu
        arraywork = ArrayList()
        loadData()

        // Khởi tạo adapter và gán cho ListView
        arrAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, arraywork)
        lv.adapter = arrAdapter

        // Hiển thị ngày hiện tại
        val currentDate = Calendar.getInstance().time
        val simpleDateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        txtdate.text = "Hôm Nay: ${simpleDateFormat.format(currentDate)}"

        // Thêm công việc mới
        btnwork.setOnClickListener {
            val work = edtwork.text.toString().trim()
            val hourStr = edth.text.toString().trim()
            val minuteStr = edtm.text.toString().trim()
            // Kiểm tra nhập đủ thông tin
            if (work.isEmpty() || hourStr.isEmpty() || minuteStr.isEmpty()) {
                AlertDialog.Builder(this)
                    .setTitle("Info missing")
                    .setMessage("Please enter all information of the work")
                    .setPositiveButton("Continue", null)
                    .show()
            } else {
                // Kiểm tra dữ liệu số giờ, phút
                val hour = hourStr.toIntOrNull()
                val minute = minuteStr.toIntOrNull()
                if (hour == null || minute == null) {
                    AlertDialog.Builder(this)
                        .setTitle("Invalid input")
                        .setMessage("Hour and minute must be numeric values!")
                        .setPositiveButton("Continue", null)
                        .show()
                } else if (hour !in 0..23) {
                    AlertDialog.Builder(this)
                        .setTitle("Invalid hour")
                        .setMessage("Hour must be between 0 and 23")
                        .setPositiveButton("Continue", null)
                        .show()
                } else if (minute !in 0..59) {
                    AlertDialog.Builder(this)
                        .setTitle("Invalid minute")
                        .setMessage("Minute must be between 0 and 59")
                        .setPositiveButton("Continue", null)
                        .show()
                } else {
                    val str = "$work - %02d:%02d".format(hour, minute) // Định dạng 2 chữ số
                    arraywork.add(str)
                    arrAdapter.notifyDataSetChanged()
                    saveData()
                    edth.setText("")
                    edtm.setText("")
                    edtwork.setText("")
                }
            }
        }


        // Xóa công việc khi click vào dòng
        lv.setOnItemClickListener { _, _, position, _ ->
            AlertDialog.Builder(this)
                .setTitle("Delete work")
                .setMessage("Do you want to delete this work?")
                .setPositiveButton("Yes") { _, _ ->
                    arraywork.removeAt(position)
                    arrAdapter.notifyDataSetChanged()
                    saveData()
                }
                .setNegativeButton("No", null)
                .show()
        }
    }

    // Lưu ArrayList vào SharedPreferences
    private fun saveData() {
        val prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE)
        val editor = prefs.edit()
        val sb = StringBuilder()
        for (s in arraywork) {
            sb.append(s.replace("|", "||")).append("|;|")
        }
        editor.putString(WORKS_KEY, sb.toString())
        editor.apply()
    }

    // Đọc ArrayList từ SharedPreferences
    private fun loadData() {
        val prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE)
        val saved = prefs.getString(WORKS_KEY, "")
        if (!saved.isNullOrEmpty()) {
            val items = saved.split("|;|")
            for (item in items) {
                if (item.isNotEmpty()) {
                    arraywork.add(item.replace("||", "|"))
                }
            }
        }
    }
}
