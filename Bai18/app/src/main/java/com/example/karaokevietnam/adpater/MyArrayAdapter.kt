package com.example.karaokevietnam.adpater

import android.content.ContentValues
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.karaokevietnam.MainActivity
import com.example.karaokevietnam.R
import com.example.karaokevietnam.model.Item
import com.example.karaokevietnam.SubActivity
import com.squareup.picasso.Picasso

class MyArrayAdapter(
    private val ctx: Context,
    private val layoutId: Int,
    private val items: MutableList<Item>
) : ArrayAdapter<Item>(ctx, layoutId, items) {

    private val URL_LIKE   = "https://cdn-icons-png.flaticon.com/512/833/833472.png"
    private val URL_UNLIKE = "https://cdn-icons-png.flaticon.com/512/1077/1077035.png"

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val row = convertView ?: LayoutInflater.from(ctx).inflate(layoutId, parent, false)
        val maso = row.findViewById<TextView>(R.id.txtmaso)
        val tieude = row.findViewById<TextView>(R.id.txttieude)
        val like = row.findViewById<ImageButton>(R.id.btnlike)
        val unlike = row.findViewById<ImageButton>(R.id.btnunlike)

        val it = items[position]
        maso.text = it.maso
        tieude.text = it.tieude

        // load icon bằng Picasso
        Picasso.get().load(URL_LIKE).into(like)
        Picasso.get().load(URL_UNLIKE).into(unlike)

        fun renderState(thich: Int) {
            if (thich == 1) { like.visibility = View.VISIBLE; unlike.visibility = View.INVISIBLE }
            else { like.visibility = View.INVISIBLE; unlike.visibility = View.VISIBLE }
        }
        renderState(it.thich)

        like.setOnClickListener {
            updateLike(maso.text.toString(), 0)
            it.visibility = View.INVISIBLE
            unlike.visibility = View.VISIBLE
            items[position].thich = 0
        }
        unlike.setOnClickListener {
            updateLike(maso.text.toString(), 1)
            it.visibility = View.INVISIBLE
            like.visibility = View.VISIBLE
            items[position].thich = 1
        }

        tieude.setOnClickListener {
            tieude.setTextColor(Color.RED)
            maso.setTextColor(Color.RED)
            SubActivity.start(ctx, maso.text.toString())
        }

        return row
    }

    private fun updateLike(maso: String, value: Int) {
        val cv = ContentValues().apply { put("YEUTHICH", value) }
        MainActivity.database.update("ArirangSongList", cv, "MABH=?", arrayOf(maso))
    }
}