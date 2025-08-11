package com.example.karaokevietnam

import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.karaokevietnam.adpater.MyArrayAdapter
import com.example.karaokevietnam.data.DbHelper
import com.example.karaokevietnam.model.Item
import com.squareup.picasso.Picasso

class MainActivity : AppCompatActivity() {

    companion object {
        lateinit var database: SQLiteDatabase
    }

    private lateinit var tab: TabHost
    private lateinit var edttim: EditText
    private lateinit var lv1: ListView
    private lateinit var lv2: ListView
    private lateinit var lv3: ListView
    private lateinit var btnxoa: ImageButton

    private val list1 = mutableListOf<Item>()
    private val list2 = mutableListOf<Item>()
    private val list3 = mutableListOf<Item>()

    private lateinit var ad1: MyArrayAdapter
    private lateinit var ad2: MyArrayAdapter
    private lateinit var ad3: MyArrayAdapter

    // icon online cho tab
    private val URL_SEARCH = "https://cdn-icons-png.flaticon.com/512/622/622669.png"
    private val URL_LIST   = "https://cdn-icons-png.flaticon.com/512/1828/1828765.png"
    private val URL_FAV    = "https://cdn-icons-png.flaticon.com/512/833/833472.png"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // mở DB (tạo + seed nếu lần đầu)
        database = DbHelper(this).writableDatabase

        bindViews()
        setupTabs()
        setupAdapters()
        setupEvents()
    }

    private fun bindViews() {
        tab = findViewById(R.id.tabhost)
        tab.setup()
        edttim = findViewById(R.id.edttim)
        btnxoa = findViewById(R.id.btnxoa)
        lv1 = findViewById(R.id.lv1)
        lv2 = findViewById(R.id.lv2)
        lv3 = findViewById(R.id.lv3)
    }

    private fun setupTabs() {
        fun makeTab(tag: String, contentId: Int, url: String): TabHost.TabSpec {
            val v = LayoutInflater.from(this).inflate(R.layout.tab_icon, null)
            val img = v.findViewById<ImageView>(R.id.img)
            Picasso.get().load(url).into(img)
            return tab.newTabSpec(tag).apply {
                setContent(contentId)
                setIndicator(v)
            }
        }

        tab.addTab(makeTab("t1", R.id.tab1, URL_SEARCH))
        tab.addTab(makeTab("t2", R.id.tab2, URL_LIST))
        tab.addTab(makeTab("t3", R.id.tab3, URL_FAV))
    }

    private fun setupAdapters() {
        ad1 = MyArrayAdapter(this, R.layout.listitem, list1)
        ad2 = MyArrayAdapter(this, R.layout.listitem, list2)
        ad3 = MyArrayAdapter(this, R.layout.listitem, list3)
        lv1.adapter = ad1
        lv2.adapter = ad2
        lv3.adapter = ad3
    }

    private fun setupEvents() {
        // tìm kiếm live
        edttim.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) = getSearch()
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable?) {}
        })
        btnxoa.setOnClickListener { edttim.setText("") }

        tab.setOnTabChangedListener {
            when (it) {
                "t2" -> loadDanhSach()
                "t3" -> loadYeuThich()
            }
        }

        // load mặc định
        loadDanhSach()
    }

    private fun getSearch() {
        val q = edttim.text.toString().trim()
        list1.clear()
        if (q.isNotEmpty()) {
            val c = database.rawQuery(
                "SELECT MABH, TENBH1, YEUTHICH FROM ArirangSongList WHERE TENBH1 LIKE ? OR MABH LIKE ?",
                arrayOf("%$q%", "%$q%")
            )
            c.use {
                while (it.moveToNext()) {
                    list1.add(Item(it.getString(0), it.getString(1), it.getInt(2)))
                }
            }
        }
        ad1.notifyDataSetChanged()
    }

    private fun loadDanhSach() {
        list2.clear()
        val c = database.rawQuery("SELECT MABH, TENBH1, YEUTHICH FROM ArirangSongList", null)
        c.use {
            while (it.moveToNext()) {
                list2.add(Item(it.getString(0), it.getString(1), it.getInt(2)))
            }
        }
        ad2.notifyDataSetChanged()
    }

    private fun loadYeuThich() {
        list3.clear()
        val c = database.rawQuery("SELECT MABH, TENBH1, YEUTHICH FROM ArirangSongList WHERE YEUTHICH=1", null)
        c.use {
            while (it.moveToNext()) {
                list3.add(Item(it.getString(0), it.getString(1), it.getInt(2)))
            }
        }
        ad3.notifyDataSetChanged()
    }
}