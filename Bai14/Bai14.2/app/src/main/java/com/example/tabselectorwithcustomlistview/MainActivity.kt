package com.example.tabselectorwithcustomlistview

import android.app.Activity
import android.os.Bundle
import android.widget.*
import com.example.tabselectorwithcustomlistview.R
import com.example.tabselectorwithcustomlistview.adapter.MyArrayAdapter
import com.example.tabselectorwithcustomlistview.model.Item
import com.squareup.picasso.Picasso

class MainActivity : Activity() {

    private lateinit var tab: TabHost
    private lateinit var edttim: EditText
    private lateinit var lv1: ListView
    private lateinit var lv2: ListView
    private lateinit var lv3: ListView

    private lateinit var list1: ArrayList<Item>
    private lateinit var list2: ArrayList<Item>
    private lateinit var list3: ArrayList<Item>

    private lateinit var myarray1: MyArrayAdapter
    private lateinit var myarray2: MyArrayAdapter
    private lateinit var myarray3: MyArrayAdapter

    // URL icon tab (đổi link tuỳ ý)
    private val urlSearch = "https://cdn-icons-png.flaticon.com/512/861/861627.pngg"
    private val urlList   = "https://cdn-icons-png.flaticon.com/512/1828/1828859.png"
    private val urlFav    = "https://cdn-icons-png.flaticon.com/512/833/833472.png"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        addControl()
        addEvent()
    }

    private fun addControl() {
        tab = findViewById(R.id.tabhost)
        tab.setup()

        // Thêm 3 tab với indicator là View + Picasso
        tab.addTab(createPicassoTab("t1", R.id.tab1, urlSearch))
        tab.addTab(createPicassoTab("t2", R.id.tab2, urlList))
        tab.addTab(createPicassoTab("t3", R.id.tab3, urlFav))

        edttim = findViewById(R.id.edttim)
        lv1 = findViewById(R.id.lv1)
        lv2 = findViewById(R.id.lv2)
        lv3 = findViewById(R.id.lv3)

        list1 = arrayListOf()
        list2 = arrayListOf()
        list3 = arrayListOf()

        myarray1 = MyArrayAdapter(this, R.layout.listitem, list1)
        myarray2 = MyArrayAdapter(this, R.layout.listitem, list2)
        myarray3 = MyArrayAdapter(this, R.layout.listitem, list3)

        lv1.adapter = myarray1
        lv2.adapter = myarray2
        lv3.adapter = myarray3

        // dữ liệu ban đầu cho tab1
        list1.add(Item("52300", "Em là ai Tôi là ai", 0))
        list1.add(Item("52600", "Bài ca đất Phương Nam", 1))
        list1.add(Item("52567", "Buồn của Anh", 1))
        myarray1.notifyDataSetChanged()
    }

    private fun addEvent() {
        // Load dữ liệu theo tab
        tab.setOnTabChangedListener { tabId ->
            when (tabId.lowercase()) {
                "t1" -> {
                    list1.clear()
                    list1.add(Item("52300", "Em là ai Tôi là ai", 0))
                    list1.add(Item("52600", "Chén Đắng", 1))
                    myarray1.notifyDataSetChanged()
                }
                "t2" -> {
                    list2.clear()
                    list2.add(Item("57236", "Gởi em ở cuối sông hồng", 0))
                    list2.add(Item("51548", "Quê hương tuổi thơ tôi", 0))
                    list2.add(Item("51748", "Em gì ơi", 0))
                    myarray2.notifyDataSetChanged()
                }
                "t3" -> {
                    list3.clear()
                    list3.add(Item("57689", "Hát với dòng sông", 1))
                    list3.add(Item("58716", "Say tình _ Remix", 0))
                    list3.add(Item("58916", "Người hãy quên em đi", 1))
                    myarray3.notifyDataSetChanged()
                }
            }
        }

        // filter nhanh trên tab1
        edttim.addTextChangedListener(object : android.text.TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: android.text.Editable?) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val q = s?.toString()?.trim()?.lowercase().orEmpty()
                val all = listOf(
                    Item("52300", "Em là ai Tôi là ai", 0),
                    Item("52600", "Bài ca đất Phương Nam", 1),
                    Item("52567", "Buồn của Anh", 1),
                )
                list1.clear()
                list1.addAll(if (q.isEmpty()) all else all.filter { it.tieude.lowercase().contains(q) })
                myarray1.notifyDataSetChanged()
            }
        })
    }

    /** Tạo TabSpec với indicator là 1 View và dùng Picasso để load icon online */
    private fun createPicassoTab(tag: String, contentId: Int, iconUrl: String): TabHost.TabSpec {
        val spec = tab.newTabSpec(tag)
        spec.setContent(contentId)

        val indicatorView = layoutInflater.inflate(R.layout.tab_indicator, null)
        val iconView = indicatorView.findViewById<ImageView>(R.id.tab_icon)

        Picasso.get()
            .load(iconUrl)
            .placeholder(R.mipmap.ic_launcher)
            .error(R.mipmap.ic_launcher)
            .into(iconView)

        spec.setIndicator(indicatorView)
        return spec
    }
}
