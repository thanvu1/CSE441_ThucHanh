package com.example.sqlite2

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class MainActivity : AppCompatActivity() {

    private val DB_PATH_SUFFIX = "/databases/"
    private val DATABASE_NAME = "qlsach.db"
    private lateinit var database: SQLiteDatabase

    private lateinit var lv: ListView
    private lateinit var mylist: ArrayList<String>
    private lateinit var myadapter: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Copy DB từ assets nếu chưa có
        copyDatabaseFromAssets()

        // Mở DB
        database = openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE, null)

        // Đảm bảo bảng tồn tại + seed dữ liệu nếu thiếu
        ensureSchemaAndSeed(database)

        // Setup ListView
        lv = findViewById(R.id.lv)
        mylist = ArrayList()
        myadapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, mylist)
        lv.adapter = myadapter

        // Load dữ liệu từ DB
        loadData()
    }

    private fun loadData() {
        mylist.clear()
        val c: Cursor = database.query("tbsach", null, null, null, null, null, null)
        if (c.moveToFirst()) {
            do {
                val data = c.getString(0) + " - " + c.getString(1) + " - " + c.getString(2)
                mylist.add(data)
            } while (c.moveToNext())
        }
        c.close()
        myadapter.notifyDataSetChanged()
    }

    // Copy DB từ assets vào thư mục databases của app
    private fun copyDatabaseFromAssets() {
        val dbFile = getDatabasePath(DATABASE_NAME)
        if (!dbFile.exists()) {
            try {
                val myInput = assets.open(DATABASE_NAME) // nếu file trống, vẫn tạo file
                val outFileName = getDatabasePath()
                val f = File(getApplicationInfo().dataDir + DB_PATH_SUFFIX)
                if (!f.exists()) f.mkdir()

                val myOutput = FileOutputStream(outFileName)
                val buffer = ByteArray(1024)
                var length: Int
                while (myInput.read(buffer).also { length = it } > 0) {
                    myOutput.write(buffer, 0, length)
                }
                myOutput.flush()
                myOutput.close()
                myInput.close()

                Toast.makeText(this, "Database copied from assets", Toast.LENGTH_SHORT).show()
            } catch (e: IOException) {
                // Nếu không có file assets, tạo file rỗng
                val f = File(getApplicationInfo().dataDir + DB_PATH_SUFFIX)
                if (!f.exists()) f.mkdir()
                openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE, null).close()
                Toast.makeText(this, "Empty database created", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun getDatabasePath(): String {
        return getApplicationInfo().dataDir + DB_PATH_SUFFIX + DATABASE_NAME
    }

    // Tạo bảng và seed dữ liệu nếu chưa tồn tại
    private fun ensureSchemaAndSeed(db: SQLiteDatabase) {
        val hasTable = db.rawQuery(
            "SELECT name FROM sqlite_master WHERE type='table' AND name=?",
            arrayOf("tbsach")
        ).use { it.moveToFirst() }

        if (!hasTable) {
            db.execSQL(
                """
                CREATE TABLE IF NOT EXISTS tbsach (
                    masach TEXT PRIMARY KEY,
                    tensach TEXT,
                    tacgia TEXT
                );
                """.trimIndent()
            )
            db.execSQL("INSERT INTO tbsach VALUES('S001','Lập trình Android cơ bản','Nguyễn A');")
            db.execSQL("INSERT INTO tbsach VALUES('S002','Cơ sở dữ liệu SQLite','Trần B');")
            db.execSQL("INSERT INTO tbsach VALUES('S003','Kotlin từ A-Z','Lê C');")
        }
    }
}
