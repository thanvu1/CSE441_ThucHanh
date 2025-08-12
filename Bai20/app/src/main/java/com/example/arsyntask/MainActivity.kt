package com.example.arsyntask

import android.app.Activity
import android.os.AsyncTask
import android.os.Bundle
import android.os.SystemClock
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var btnStart: Button
    private var myTask: MyAsyncTask? = null

    @Suppress("DEPRECATION") // AsyncTask is deprecated but required for this exercise
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnStart = findViewById(R.id.button1)

        btnStart.setOnClickListener {
            // Avoid launching multiple tasks at once
            if (myTask?.status == AsyncTask.Status.RUNNING) return@setOnClickListener
            myTask = MyAsyncTask(this)
            myTask?.execute()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        // Clean up if the Activity is finishing
        myTask?.cancel(true)
    }
}

/** Kotlin port of your AsyncTask */
@Suppress("DEPRECATION")
class MyAsyncTask(private val contextCha: Activity)
    : AsyncTask<Void, Int, Void>() {

    override fun onPreExecute() {
        super.onPreExecute()
        Toast.makeText(contextCha, "onPreExecute!", Toast.LENGTH_SHORT).show()

        // Reset UI
        val pb = contextCha.findViewById<ProgressBar>(R.id.progressBar1)
        val tv = contextCha.findViewById<TextView>(R.id.textView1)
        pb.progress = 0
        tv.text = "0%"
    }

    override fun doInBackground(vararg params: Void?): Void? {
        for (i in 0..100) {
            if (isCancelled) break
            SystemClock.sleep(100)        // simulate work
            publishProgress(i)            // triggers onProgressUpdate
        }
        return null
    }

    override fun onProgressUpdate(vararg values: Int?) {
        super.onProgressUpdate(*values)
        val value = values.firstOrNull() ?: return
        val pb = contextCha.findViewById<ProgressBar>(R.id.progressBar1)
        val tv = contextCha.findViewById<TextView>(R.id.textView1)
        pb.progress = value
        tv.text = "$value%"
    }

    override fun onPostExecute(result: Void?) {
        super.onPostExecute(result)
        Toast.makeText(contextCha, "Update xong rồi đó!", Toast.LENGTH_LONG).show()
    }
}