package com.example.internetworking

import android.os.AsyncTask
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import java.io.StringReader

class MainActivity : AppCompatActivity() {

    private lateinit var btnParse: Button
    private lateinit var listView: ListView
    private lateinit var adapter: ArrayAdapter<String>
    private val myList = ArrayList<String>()

    private val URL = "https://www.w3schools.com/xml/plant_catalog.xml"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnParse = findViewById(R.id.btnParse)
        listView = findViewById(R.id.listView)

        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, myList)
        listView.adapter = adapter

        btnParse.setOnClickListener {
            LoadXMLTask().execute()
        }
    }

    inner class LoadXMLTask : AsyncTask<Void, Void, ArrayList<String>>() {
        override fun onPreExecute() {
            super.onPreExecute()
            myList.clear()
        }

        override fun doInBackground(vararg params: Void?): ArrayList<String> {
            val resultList = ArrayList<String>()
            try {
                val parserFactory = XmlPullParserFactory.newInstance()
                val parser = parserFactory.newPullParser()

                val xmlParser = XMLParser()
                val xmlData = xmlParser.getXmlFromUrl(URL)

                parser.setInput(StringReader(xmlData))

                var eventType = parser.eventType
                var dataShow = ""
                var common = ""
                var price = ""

                while (eventType != XmlPullParser.END_DOCUMENT) {
                    when (eventType) {
                        XmlPullParser.START_TAG -> {
                            val tagName = parser.name
                            when (tagName) {
                                "COMMON" -> common = parser.nextText()
                                "PRICE" -> price = parser.nextText()
                            }
                        }
                        XmlPullParser.END_TAG -> {
                            if (parser.name == "PLANT") {
                                dataShow = "$common - $price"
                                resultList.add(dataShow)
                                common = ""
                                price = ""
                            }
                        }
                    }
                    eventType = parser.next()
                }

            } catch (e: Exception) {
                e.printStackTrace()
            }
            return resultList
        }

        override fun onPostExecute(result: ArrayList<String>) {
            super.onPostExecute(result)
            myList.clear()
            myList.addAll(result)
            adapter.notifyDataSetChanged()
        }
    }
}