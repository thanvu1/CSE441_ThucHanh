package com.example.quanlysinhvien

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var edtmalop: EditText
    private lateinit var edttenlop: EditText
    private lateinit var edtsiso: EditText
    private lateinit var btninsert: Button
    private lateinit var btndelete: Button
    private lateinit var btnupdate: Button
    private lateinit var btnquery: Button
    private lateinit var lv: ListView

    private lateinit var mydatabase: SQLiteDatabase
    private lateinit var mylist: ArrayList<String>
    private lateinit var myadapter: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bindViews()
        setupListView()
        openDbAndCreateTable()

        // INSERT
        btninsert.setOnClickListener {
            val malop = edtmalop.text.toString().trim()
            val tenlop = edttenlop.text.toString().trim()
            val siso = edtsiso.text.toString().toIntOrNull()

            if (malop.isEmpty() || tenlop.isEmpty() || siso == null) {
                toast("Vui lòng nhập đầy đủ và đúng định dạng.")
                return@setOnClickListener
            }

            val values = ContentValues().apply {
                put("malop", malop)
                put("tenlop", tenlop)
                put("siso", siso)
            }

            val rowId = mydatabase.insert("tbllop", null, values)
            if (rowId == -1L) toast("Fail to insert record!")
            else toast("Insert record successfully")
        }

        // DELETE theo mã lớp
        btndelete.setOnClickListener {
            val malop = edtmalop.text.toString().trim()
            if (malop.isEmpty()) {
                toast("Nhập mã lớp để xóa")
                return@setOnClickListener
            }
            val n = mydatabase.delete("tbllop", "malop = ?", arrayOf(malop))
            toast(if (n == 0) "No record to delete" else "$n record(s) deleted")
        }

        // UPDATE sĩ số theo mã lớp
        btnupdate.setOnClickListener {
            val malop = edtmalop.text.toString().trim()
            val siso = edtsiso.text.toString().toIntOrNull()

            if (malop.isEmpty() || siso == null) {
                toast("Nhập mã lớp và sĩ số hợp lệ để cập nhật")
                return@setOnClickListener
            }

            val values = ContentValues().apply { put("siso", siso) }
            val n = mydatabase.update("tbllop", values, "malop = ?", arrayOf(malop))
            toast(if (n == 0) "No record to update" else "$n record(s) updated")
        }

        // QUERY tất cả và đổ ra ListView
        btnquery.setOnClickListener {
            refreshList()
        }
    }

    private fun bindViews() {
        edtmalop = findViewById(R.id.edtmalop)
        edttenlop = findViewById(R.id.edttenlop)
        edtsiso = findViewById(R.id.edtsiso)
        btninsert = findViewById(R.id.btninsert)
        btndelete = findViewById(R.id.btndelete)
        btnupdate = findViewById(R.id.btnupdate)
        btnquery = findViewById(R.id.btnquery)
        lv = findViewById(R.id.lv)
    }

    private fun setupListView() {
        mylist = ArrayList()
        myadapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, mylist)
        lv.adapter = myadapter
    }

    private fun openDbAndCreateTable() {
        // Mở / tạo database
        mydatabase = openOrCreateDatabase("qlsinhvien.db", MODE_PRIVATE, null)

        // Tạo table (nếu chưa có)
        try {
            val sql = """
                CREATE TABLE IF NOT EXISTS tbllop(
                    malop TEXT PRIMARY KEY,
                    tenlop TEXT,
                    siso INTEGER
                )
            """.trimIndent()
            mydatabase.execSQL(sql)
        } catch (e: Exception) {
            Log.e("DB", "CREATE TABLE error: ${e.message}")
        }
    }

    private fun refreshList() {
        mylist.clear()
        val c: Cursor = mydatabase.query(
            "tbllop",
            arrayOf("malop", "tenlop", "siso"),
            null, null, null, null, null
        )

        c.use { cursor ->
            while (cursor.moveToNext()) {
                val malop = cursor.getString(0)
                val tenlop = cursor.getString(1)
                val siso = cursor.getInt(2)
                mylist.add("$malop - $tenlop - $siso")
            }
        }

        myadapter.notifyDataSetChanged()
    }

    private fun toast(msg: String) =
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()

    override fun onDestroy() {
        super.onDestroy()
        if (this::mydatabase.isInitialized) mydatabase.close()
    }
}
