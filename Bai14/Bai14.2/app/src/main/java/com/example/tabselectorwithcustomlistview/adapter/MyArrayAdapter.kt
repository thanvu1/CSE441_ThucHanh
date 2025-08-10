package com.example.tabselectorwithcustomlistview.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.tabselectorwithcustomlistview.R
import com.example.tabselectorwithcustomlistview.model.Item
import com.squareup.picasso.Picasso

class MyArrayAdapter(
    private val ctx: Activity,
    private val layoutId: Int,
    private val data: ArrayList<Item>
) : ArrayAdapter<Item>(ctx, layoutId, data) {

    // URL icon online (bạn đổi link khác tuỳ ý)
    private val likeUrl =
        "https://cdn-icons-png.flaticon.com/512/833/833472.png"     // trái tim đầy
    private val unlikeUrl =
        "https://cdn-icons-png.flaticon.com/512/1077/1077035.png"   // trái tim rỗng

    private class VH(v: View) {
        val maso: TextView = v.findViewById(R.id.txtmaso)
        val tieude: TextView = v.findViewById(R.id.txttieude)
        val like: ImageView = v.findViewById(R.id.btnlike)
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val row = convertView ?: LayoutInflater.from(ctx).inflate(layoutId, parent, false)
        val holder: VH = (row.tag as? VH) ?: VH(row).also { row.tag = it }

        val item = data[position]
        holder.maso.text = item.maso
        holder.tieude.text = item.tieude

        val url = if (item.thich == 1) likeUrl else unlikeUrl
        Picasso.get()
            .load(url)
            .placeholder(R.mipmap.ic_launcher)
            .error(R.mipmap.ic_launcher)
            .into(holder.like)

        // (tuỳ chọn) toggle like/unlike khi bấm ảnh
        holder.like.setOnClickListener {
            item.thich = if (item.thich == 1) 0 else 1
            notifyDataSetChanged()
        }

        return row
    }
}
