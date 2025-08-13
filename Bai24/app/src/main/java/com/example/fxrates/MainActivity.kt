package com.example.fxrates

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.fxrates.adapter.TyGiaAdapter
import com.example.fxrates.databinding.ActivityMainBinding
import com.example.fxrates.model.TyGia
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import org.jsoup.Jsoup
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val data: MutableList<TyGia> = mutableListOf()
    private lateinit var adapter: TyGiaAdapter

    // URL & User-Agent theo yêu cầu
    private val HSBC_URL = "https://www.hsbc.com.vn/en-vn/foreign-exchange/rate/"
    private val UA_S25 =
        "Mozilla/5.0 (Linux; Android 15; SM-S931B Build/AP3A.240905.015.A2; wv) " +
                "AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 " +
                "Chrome/127.0.6533.103 Mobile Safari/537.36"

    // map mã -> cờ (flagcdn)
    private val flagMap = mapOf(
        "USD" to "us","EUR" to "eu","GBP" to "gb","JPY" to "jp",
        "AUD" to "au","CAD" to "ca","SGD" to "sg","CNY" to "cn",
        "KRW" to "kr","INR" to "in","THB" to "th","HKD" to "hk",
        "VND" to "vn","NZD" to "nz","CHF" to "ch"
    )

    private val client by lazy { OkHttpClient.Builder().build() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setToday()
        adapter = TyGiaAdapter(this, data)
        binding.lv1.adapter = adapter

        // gọi coroutine
        lifecycleScope.launch {
            binding.txtdate.append("\nĐang tải từ HSBC…")
            val list = fetchRatesFromHsbc()
            data.clear()
            data.addAll(list)
            adapter.notifyDataSetChanged()

            if (list.isEmpty()) {
                binding.txtdate.append("\nKhông lấy được dữ liệu (HSBC đổi DOM?)")
            } else {
                binding.txtdate.append("\nĐã cập nhật ${list.size} dòng.")
            }
        }
    }

    private fun setToday() {
        val today = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date())
        binding.txtdate.text = "TỈ GIÁ HỐI ĐOÁI — Hôm nay: $today"
    }

    /** chạy trên IO thread */
    private suspend fun fetchRatesFromHsbc(): List<TyGia> = withContext(Dispatchers.IO) {
        fun flagOf(ccy: String): String {
            val map = mapOf(
                "USD" to "us","EUR" to "eu","GBP" to "gb","JPY" to "jp",
                "AUD" to "au","CAD" to "ca","SGD" to "sg","CNY" to "cn",
                "KRW" to "kr","INR" to "in","THB" to "th","HKD" to "hk",
                "VND" to "vn","NZD" to "nz","CHF" to "ch"
            )
            return map[ccy] ?: "un"
        }

        try {
            // 1) Tải HTML HSBC
            val req = Request.Builder()
                .url(HSBC_URL)
                .header("User-Agent", UA_S25)
                .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
                .header("Accept-Language", "vi-VN,vi;q=0.9,en-US;q=0.8")
                .build()

            val html = client.newCall(req).execute().use { it.body?.string().orEmpty() }
            if (html.isBlank()) return@withContext emptyList<TyGia>()

            val out = mutableListOf<TyGia>()
            val doc = Jsoup.parse(html)

            // ---------- (A) THỬ TỪ BẢNG HTML ----------
            runCatching {
                val tables = doc.select("table")
                for (tb in tables) {
                    for (tr in tb.select("tr")) {
                        val tds = tr.select("td")
                        if (tds.size < 5) continue
                        val c0 = tds[0].text().trim().uppercase(Locale.ROOT)
                        if (!c0.matches(Regex("^[A-Z]{3,4}$"))) continue

                        val buyCash  = tds[1].text().trim()
                        val buyTT    = tds[2].text().trim()
                        val sellCash = tds[3].text().trim()
                        val sellTT   = tds[4].text().trim()

                        val img = "https://flagcdn.com/w40/${flagOf(c0.take(3))}.png"
                        out += TyGia(c0.take(3), img, buyCash, buyTT, sellCash, sellTT)
                    }
                }
            }

            if (out.isNotEmpty()) return@withContext out

            // ---------- (B) THỬ TỪ JSON NHÚNG TRONG SCRIPT ----------
            // Tìm script chứa các khóa “currency” và “rate”
            val scripts = doc.select("script")
            for (sc in scripts) {
                val s = sc.data()
                if (!(s.contains("currency", true) && s.contains("rate", true))) continue

                // Lấy tất cả object có currency code và các trường buy/sell
                // Regex “mềm” để bắt nhiều biến thể tên trường:
                val objRegex = Regex("""\{[^{}]*?(currency(Code)?|ccy)\s*:\s*["']([A-Z]{3})["'][\s\S]*?\}""")
                val buyCashR = Regex("""(buy(Cash)?(Rate)?|cash(Buy)?)(\s*:\s*["']?)([0-9\.,]+)""", RegexOption.IGNORE_CASE)
                val buyTTR   = Regex("""(buy(TT|Transfer)?(Rate)?|tt(Buy)?)(\s*:\s*["']?)([0-9\.,]+)""", RegexOption.IGNORE_CASE)
                val sellCashR= Regex("""(sell(Cash)?(Rate)?|cash(Sell)?)(\s*:\s*["']?)([0-9\.,]+)""", RegexOption.IGNORE_CASE)
                val sellTTR  = Regex("""(sell(TT|Transfer)?(Rate)?|tt(Sell)?)(\s*:\s*["']?)([0-9\.,]+)""", RegexOption.IGNORE_CASE)

                objRegex.findAll(s).forEach { m ->
                    val block = m.value
                    val ccy = Regex("""(["']?)([A-Z]{3})\1""").find(block)?.groupValues?.get(2) ?: return@forEach
                    fun pick(r: Regex) = r.find(block)?.groupValues?.last()?.trim().orEmpty()

                    val buyCash  = pick(buyCashR)
                    val buyTT    = pick(buyTTR)
                    val sellCash = pick(sellCashR)
                    val sellTT   = pick(sellTTR)

                    if (listOf(buyCash, buyTT, sellCash, sellTT).any { it.isNotBlank() }) {
                        val img = "https://flagcdn.com/w40/${flagOf(ccy)}.png"
                        out += TyGia(ccy, img,
                            muaTienMat = buyCash,
                            muaCK = buyTT,
                            banTienMat = sellCash,
                            banCK = sellTT
                        )
                    }
                }
                if (out.isNotEmpty()) break
            }

            if (out.isNotEmpty()) return@withContext out

            // ---------- (C) FALLBACK: LẤY TỪ ECB (để test UI chạy OK) ----------
            // Bạn có thể bỏ nhánh này khi tìm được API JSON chính thức của HSBC.
            val ecbReq = Request.Builder()
                .url("https://www.ecb.europa.eu/stats/eurofxref/eurofxref-daily.xml")
                .header("User-Agent", UA_S25)
                .build()

            client.newCall(ecbReq).execute().use { resp ->
                val xml = resp.body?.string().orEmpty()
                if (xml.isBlank()) return@withContext emptyList<TyGia>()
                val ecb = Jsoup.parse(xml, "", org.jsoup.parser.Parser.xmlParser())
                val cubes = ecb.select("Cube[currency][rate]")
                val list = cubes.map { el ->
                    val ccy = el.attr("currency")
                    val rate = el.attr("rate")
                    val img = "https://flagcdn.com/w40/${flagOf(ccy)}.png"
                    // ECB cho 1 tỷ giá EUR→CCY; Hiển thị cùng 4 cột cho dễ nhìn
                    TyGia(ccy, img, rate, rate, rate, rate)
                }
                return@withContext list
            }
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }
}
