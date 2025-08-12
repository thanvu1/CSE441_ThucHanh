package com.example.internetworking

import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class XMLParser {
    fun getXmlFromUrl(urlString: String): String? {
        var xml: String? = null
        try {
            val url = URL(urlString)
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "GET"
            connection.connectTimeout = 5000
            connection.readTimeout = 5000

            val reader = BufferedReader(InputStreamReader(connection.inputStream))
            val stringBuilder = StringBuilder()
            var line: String?

            while (reader.readLine().also { line = it } != null) {
                stringBuilder.append(line)
            }

            reader.close()
            xml = stringBuilder.toString()
            connection.disconnect()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return xml
    }
}