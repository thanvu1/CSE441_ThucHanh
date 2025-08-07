package com.example.imggridview.adapter

import android.content.Context
import android.view.*
import android.widget.BaseAdapter
import android.widget.ImageView
import com.example.imggridview.R
import com.squareup.picasso.Picasso

class ImageAdapter(private val context: Context, private val imageUrls: List<String>) : BaseAdapter() {
    override fun getCount() = imageUrls.size
    override fun getItem(position: Int) = imageUrls[position]
    override fun getItemId(position: Int) = position.toLong()
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View = convertView ?: LayoutInflater.from(context).inflate(R.layout.item_image, parent, false)
        val imgThumb = view.findViewById<ImageView>(R.id.imgThumb)
        Picasso.get()
            .load(imageUrls[position])
            .placeholder(android.R.drawable.ic_menu_gallery)
            .error(android.R.drawable.ic_delete)
            .into(imgThumb)
        return view
    }
}