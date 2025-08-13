package com.example.news.net

import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class XMLDownloader {
    fun getXmlFromUrl(urlStr: String): String? {
        val url = URL(urlStr)
        val conn = (url.openConnection() as HttpURLConnection).apply {
            connectTimeout = 10000
            readTimeout = 10000
            requestMethod = "GET"
        }
        return conn.inputStream.use { input ->
            BufferedReader(InputStreamReader(input)).use { br ->
                buildString {
                    var line: String?
                    while (true) {
                        line = br.readLine() ?: break
                        append(line).append('\n')
                    }
                }
            }
        }
    }
}
