package com.example.jsonparsing

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.nio.charset.Charset

class MainActivity : AppCompatActivity() {

    private lateinit var btnParse: Button
    private lateinit var lv: ListView
    private val myList = ArrayList<String>()
    private lateinit var myAdapter: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnParse = findViewById(R.id.btnparse)
        lv = findViewById(R.id.lv)

        myAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            myList
        )
        lv.adapter = myAdapter

        btnParse.setOnClickListener { parseJson() }
    }

    private fun parseJson() {
        try {
            // Đọc file assets/computer.json thành String (UTF-8)
            val jsonText = assets.open("computer.json").use { input ->
                val bytes = ByteArray(input.available())
                input.read(bytes)
                String(bytes, Charset.forName("UTF-8"))
            }

            val reader = JSONObject(jsonText)

            // Lấy MaDM, TenDM
            myList.clear()
            myList.add(reader.optString("MaDM"))
            myList.add(reader.optString("TenDM"))

            // Lặp mảng Sanphams
            val arr: JSONArray = reader.getJSONArray("Sanphams")
            for (i in 0 until arr.length()) {
                val obj = arr.getJSONObject(i)

                val ma = obj.optString("MaSP")
                val ten = obj.optString("TenSP")
                val soLuong = obj.optString("SoLuong")    // nếu là số, optString vẫn trả về chuỗi
                val donGia = obj.optString("DonGia")
                val thanhTien = obj.optString("ThanhTien")
                val hinh = obj.optString("Hinh")

                myList.add("$ma - $ten")
                myList.add("$soLuong * $donGia = $thanhTien")
                myList.add(hinh)
            }

            myAdapter.notifyDataSetChanged()

        } catch (io: IOException) {
            io.printStackTrace()
            // Có thể show Toast nếu muốn
            // Toast.makeText(this, "Không đọc được file JSON", Toast.LENGTH_SHORT).show()
        } catch (je: JSONException) {
            je.printStackTrace()
            // Toast.makeText(this, "Lỗi định dạng JSON", Toast.LENGTH_SHORT).show()
        }
    }
}