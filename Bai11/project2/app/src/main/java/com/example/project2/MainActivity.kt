package com.example.project2


import android.os.Bundle
import android.webkit.WebView
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val intent = getIntent()
        val data = intent.getData()

        try {
            val webView = findViewById<WebView?>(R.id.webview1)
            webView.loadUrl(data.toString())
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}