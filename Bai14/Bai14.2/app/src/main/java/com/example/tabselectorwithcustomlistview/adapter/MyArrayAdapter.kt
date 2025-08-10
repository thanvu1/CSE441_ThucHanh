package com.example.tabselectorwithcustomlistview.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageButton
import android.widget.TextView

class MyArrayAdapter(
    private val ctx: Activity,
    private val layoutId: Int,
    private val data: ArrayList<Item>
) : ArrayAdapter<Item>(ctx, layoutId, data) {

    private class VH(v: View) {
        val maso: TextView = v.findViewById(R.id.txtmaso)
        val tieude: TextView = v.findViewById(R.id.txttieude)
        val like: ImageButton = v.findViewById(R.id.btnlike)
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val row = convertView ?: LayoutInflater.from(ctx).inflate(layoutId, parent, false)
        val holder: VH = (row.tag as? VH) ?: VH(row).also { row.tag = it }

        val item = data[position]
        holder.maso.text = item.maso
        holder.tieude.text = item.tieude
        holder.like.setImageResource(if (item.thich == 1) R.drawable.like else R.drawable.unlike)

        // (tuỳ chọn) Cho phép bấm vào trái tim để toggle like/unlike
        holder.like.setOnClickListener {
            item.thich = if (item.thich == 1) 0 else 1
            notifyDataSetChanged()
        }

        return row
    }
}