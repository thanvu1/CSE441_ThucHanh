package com.example.customlistview.adapter

import com.example.customlistview.model.Phone
import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.customlistview.R
import com.squareup.picasso.Picasso

class MyArrayAdapter(
    private val context: Activity,
    private val layoutId: Int,
    private val phoneList: ArrayList<Phone>
) : ArrayAdapter<Phone>(context, layoutId, phoneList) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val rowView = LayoutInflater.from(context).inflate(layoutId, parent, false)
        val imgPhone = rowView.findViewById<ImageView>(R.id.imgPhone)
        val txtName = rowView.findViewById<TextView>(R.id.txtPhone)

        val phone = phoneList[position]
        txtName.text = phone.name
        // Sử dụng Picasso để load ảnh
        Picasso.get()
            .load(phone.imageUrl)
            .placeholder(R.drawable.ic_launcher_background) // ảnh tạm khi loading
            .error(R.drawable.ic_launcher_foreground) // ảnh khi lỗi
            .into(imgPhone)
        return rowView
    }
}
