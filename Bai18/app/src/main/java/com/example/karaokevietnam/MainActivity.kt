package com.example.karaokevietnam

import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import android.content.Intent
import com.example.karaokevietnam.adpater.MyArrayAdapter
import com.example.karaokevietnam.model.Item
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.io.OutputStream

class MainActivity : AppCompatActivity() {

    companion object {
        private const val DB_PATH_SUFFIX = "/databases/"
        const val DATABASE_NAME = "arirang.sqlite"
        lateinit var database: SQLiteDatabase

        fun openDetail(ctx: android.app.Activity, maso: String) {
            val i = Intent(ctx, SubActivity::class.java)
            i.putExtra("maso", maso)
            ctx.startActivity(i)
        }
    }

    private lateinit var edttim: EditText
    private lateinit var btnxoa: ImageButton
    private lateinit var lv1: ListView
    private lateinit var lv2: ListView
    private lateinit var lv3: ListView

    private val list1 = arrayListOf<Item>()
    private val list2 = arrayListOf<Item>()
    private val list3 = arrayListOf<Item>()

    private lateinit var adp1: MyArrayAdapter
    private lateinit var adp2: MyArrayAdapter
    private lateinit var adp3: MyArrayAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 1) Copy DB từ assets nếu chưa có
        processCopy()

        // 2) Mở DB
        database = openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null)

        // 3) Gán view + tabs
        addControl()

        // 4) Tìm kiếm live theo tên/mã
        addSearch()

        // 5) Sự kiện tab và nút xóa
        addEvents()
    }

    private fun addControl() {
        val tab = findViewById<TabHost>(R.id.tabhost).apply { setup() }

        val tab1 = tab.newTabSpec("t1").apply {
            setContent(R.id.tab1)
            setIndicator("", ContextCompat.getDrawable(this@MainActivity, R.drawable.search))
        }
        val tab2 = tab.newTabSpec("t2").apply {
            setContent(R.id.tab2)
            setIndicator("", ContextCompat.getDrawable(this@MainActivity, R.drawable.list))
        }
        val tab3 = tab.newTabSpec("t3").apply {
            setContent(R.id.tab3)
            setIndicator("", ContextCompat.getDrawable(this@MainActivity, R.drawable.favourite))
        }
        tab.addTab(tab1); tab.addTab(tab2); tab.addTab(tab3)

        edttim = findViewById(R.id.edttim)
        btnxoa = findViewById(R.id.btnxoa)
        lv1 = findViewById(R.id.lv1)
        lv2 = findViewById(R.id.lv2)
        lv3 = findViewById(R.id.lv3)

        adp1 = MyArrayAdapter(this, R.layout.listitem, list1)
        adp2 = MyArrayAdapter(this, R.layout.listitem, list2)
        adp3 = MyArrayAdapter(this, R.layout.listitem, list3)
        lv1.adapter = adp1; lv2.adapter = adp2; lv3.adapter = adp3
    }

    private fun addEvents() {
        val tab = findViewById<TabHost>(R.id.tabhost)
        tab.setOnTabChangedListener { id ->
            when (id) {
                "t2" -> loadAll()
                "t3" -> loadFavorite()
            }
        }
        btnxoa.setOnClickListener { edttim.setText("") }
    }

    private fun addSearch() {
        edttim.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence?, st: Int, b: Int, c: Int) = loadSearch()
            override fun beforeTextChanged(s: CharSequence?, st: Int, c: Int, a: Int) {}
            override fun afterTextChanged(s: Editable?) {}
        })
        loadSearch()
    }

    private fun loadSearch() {
        val key = edttim.text.toString()
        list1.clear()
        if (key.isNotEmpty()) {
            val c = database.rawQuery(
                "SELECT * FROM ArirangSongList WHERE TENBH1 LIKE ? OR MABH LIKE ?",
                arrayOf("%$key%", "%$key%")
            )
            if (c.moveToFirst()) {
                do {
                    list1.add(Item(c.getString(1), c.getString(2), c.getInt(6)))
                } while (c.moveToNext())
            }
            c.close()
        }
        adp1.notifyDataSetChanged()
    }

    private fun loadAll() {
        list2.clear()
        val c = database.rawQuery("SELECT * FROM ArirangSongList", null)
        if (c.moveToFirst()) {
            do list2.add(Item(c.getString(1), c.getString(2), c.getInt(6)))
            while (c.moveToNext())
        }
        c.close()
        adp2.notifyDataSetChanged()
    }

    private fun loadFavorite() {
        list3.clear()
        val c = database.rawQuery("SELECT * FROM ArirangSongList WHERE YEUTHICH=1", null)
        if (c.moveToFirst()) {
            do list3.add(Item(c.getString(1), c.getString(2), c.getInt(6)))
            while (c.moveToNext())
        }
        c.close()
        adp3.notifyDataSetChanged()
    }

    /* ---------- copy DB từ assets ---------- */
    private fun appDbPath(): String = applicationInfo.dataDir + DB_PATH_SUFFIX + DATABASE_NAME

    private fun processCopy() {
        val dbFile = getDatabasePath(DATABASE_NAME)
        if (!dbFile.exists()) {
            try {
                copyDbFromAssets()
                Toast.makeText(this, "Đã copy CSDL từ assets", Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun copyDbFromAssets() {
        val input: InputStream = assets.open(DATABASE_NAME)
        val dir = File(applicationInfo.dataDir + DB_PATH_SUFFIX)
        if (!dir.exists()) dir.mkdir()
        val outFile = File(appDbPath())
        val output: OutputStream = FileOutputStream(outFile)
        val buffer = ByteArray(1024)
        var len: Int
        while (input.read(buffer).also { len = it } > 0) output.write(buffer, 0, len)
        output.flush(); output.close(); input.close()
    }
}