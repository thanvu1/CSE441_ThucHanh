package com.example.customlayoutver2.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.customlayoutver2.R
import com.example.customlayoutver2.model.Phone
import com.squareup.picasso.Picasso

class MyArrayAdapter(
    private val context: Activity,
    private val resource: Int,
    private val objects: List<Phone>
) : ArrayAdapter<Phone>(context, resource, objects) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val rowView = convertView ?: LayoutInflater.from(context).inflate(resource, parent, false)
        val phone = objects[position]

        val imgPhone = rowView.findViewById<ImageView>(R.id.imgPhone)
        val txtName = rowView.findViewById<TextView>(R.id.txtPhoneName)
        val txtPrice = rowView.findViewById<TextView>(R.id.txtPhonePrice)

        txtName.text = phone.name
        txtPrice.text = "Giá bán: ${phone.price}"
        Picasso.get().load(phone.imageUrl).placeholder(R.drawable.ic_launcher_foreground).into(imgPhone)

        return rowView
    }
}
