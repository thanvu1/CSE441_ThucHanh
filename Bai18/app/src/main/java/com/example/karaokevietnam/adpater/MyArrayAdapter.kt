package com.example.karaokevietnam.adpater

import android.app.Activity
import android.content.ContentValues
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.karaokevietnam.MainActivity
import com.example.karaokevietnam.R
import com.example.karaokevietnam.model.Item

class MyArrayAdapter(
    private val ctx: Activity,
    private val layoutId: Int,
    private val items: MutableList<Item>
) : ArrayAdapter<Item>(ctx, layoutId, items) {

    private class VH(v: View) {
        val maso: TextView = v.findViewById(R.id.txtmaso)
        val tieude: TextView = v.findViewById(R.id.txttieude)
        val like: ImageView = v.findViewById(R.id.btnlike)
        val unlike: ImageView = v.findViewById(R.id.btnunlike)
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val row = convertView ?: LayoutInflater.from(ctx).inflate(layoutId, parent, false)
        val h = (row.tag as? VH) ?: VH(row).also { row.tag = it }
        val it = items[position]

        h.maso.text = it.maso
        h.tieude.text = it.tieude

        // hiển thị nút tim
        if (it.thich == 1) {
            h.like.visibility = View.VISIBLE
            h.unlike.visibility = View.INVISIBLE
        } else {
            h.like.visibility = View.INVISIBLE
            h.unlike.visibility = View.VISIBLE
        }

        // toggle yêu thích
        h.like.setOnClickListener {
            updateLike(h.maso.text.toString(), 0)
            items[position].thich = 0
            notifyDataSetChanged()
        }
        h.unlike.setOnClickListener {
            updateLike(h.maso.text.toString(), 1)
            items[position].thich = 1
            notifyDataSetChanged()
        }

        // mở chi tiết
        h.tieude.setOnClickListener {
            h.tieude.setTextColor(Color.RED)
            h.maso.setTextColor(Color.RED)
            MainActivity.openDetail(ctx, h.maso.text.toString())
        }

        return row
    }

    private fun updateLike(maso: String, value: Int) {
        val cv = ContentValues().apply { put("YEUTHICH", value) }
        MainActivity.database.update("ArirangSongList", cv, "MABH=?", arrayOf(maso))
    }
}