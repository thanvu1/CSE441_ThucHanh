package com.example.news

import android.content.Intent
import android.net.Uri
import android.os.AsyncTask
import android.os.Bundle
import android.util.Xml
import android.widget.AdapterView
import androidx.appcompat.app.AppCompatActivity
import com.example.news.adapter.ArticleAdapter
import com.example.news.databinding.ActivityMainBinding
import org.xmlpull.v1.XmlPullParser
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.StringReader
import java.net.HttpURLConnection
import java.net.URL

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val FEED_URL = "https://feeds.bbci.co.uk/news/world/rss.xml"

    private val data = mutableListOf<Article>()
    private lateinit var adapter: ArticleAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = ArticleAdapter(this, data)
        binding.lvArticles.adapter = adapter

        binding.lvArticles.onItemClickListener =
            AdapterView.OnItemClickListener { _, _, position, _ ->
                val link = data[position].link
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(link)))
            }

        LoadRssTask().execute()
    }

    /** AsyncTask để tải & parse RSS (đơn giản, không dùng coroutines) */
    inner class LoadRssTask : AsyncTask<Void, Void, ArrayList<Article>>() {
        override fun onPreExecute() {
            super.onPreExecute()
            data.clear()
            adapter.notifyDataSetChanged()
        }

        override fun doInBackground(vararg params: Void?): ArrayList<Article> {
            val xml = fetchXml(FEED_URL) ?: return arrayListOf()
            return parseRss(xml)
        }

        override fun onPostExecute(result: ArrayList<Article>) {
            super.onPostExecute(result)
            data.clear()
            data.addAll(result)
            adapter.notifyDataSetChanged()
        }
    }

    /** Tải toàn bộ XML thành String */
    private fun fetchXml(urlStr: String): String? {
        return try {
            val url = URL(urlStr)
            val conn = (url.openConnection() as HttpURLConnection).apply {
                connectTimeout = 15000
                readTimeout = 15000
                requestMethod = "GET"
            }
            conn.inputStream.use { ins ->
                BufferedReader(InputStreamReader(ins)).use { br ->
                    br.readText()
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    /** Parse RSS BBC: lấy title/link/description và ảnh từ media:thumbnail|media:content */
    private fun parseRss(xml: String): ArrayList<Article> {
        val list = ArrayList<Article>()
        val parser = Xml.newPullParser()
        parser.setInput(StringReader(xml))

        var event = parser.eventType
        var insideItem = false

        var title = ""
        var link = ""
        var description = ""
        var imageUrl: String? = null

        while (event != XmlPullParser.END_DOCUMENT) {
            when (event) {
                XmlPullParser.START_TAG -> {
                    when (parser.name) {
                        "item" -> {
                            insideItem = true
                            title = ""; link = ""; description = ""; imageUrl = null
                        }
                        "title" -> if (insideItem) title = parser.nextText()
                        "link" -> if (insideItem) link = parser.nextText()
                        "description" -> if (insideItem) description = parser.nextText()
                        "media:thumbnail", "media:content" -> if (insideItem) {
                            // lấy thuộc tính "url" (namespace có thể null)
                            imageUrl = parser.getAttributeValue(null, "url")
                            if (imageUrl.isNullOrBlank()) {
                                // fallback: duyệt mọi attr để tìm "url"
                                for (i in 0 until parser.attributeCount) {
                                    if (parser.getAttributeName(i).equals("url", true)) {
                                        imageUrl = parser.getAttributeValue(i)
                                        break
                                    }
                                }
                            }
                        }
                    }
                }
                XmlPullParser.END_TAG -> {
                    if (parser.name == "item" && insideItem) {
                        val summary = description
                            .replace(Regex("<[^>]*>"), "") // bỏ HTML
                            .replace("&quot;", "\"")
                            .replace("&apos;", "'")
                            .replace("&amp;", "&")
                            .trim()
                        if (title.isNotBlank() && link.isNotBlank()) {
                            list.add(Article(imageUrl, title.trim(), summary, link.trim()))
                        }
                        insideItem = false
                    }
                }
            }
            event = parser.next()
        }
        return list
    }
}
