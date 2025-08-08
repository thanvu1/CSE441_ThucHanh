package com.example.customlayoutver3.adpater

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.provider.ContactsContract
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.customlayoutver3.R
import com.example.customlayoutver3.model.Contact
import com.squareup.picasso.Picasso

class MyArrayAdapter(
    val context: Activity,
    val resource: Int,
    val objects: List<Contact>
) : ArrayAdapter<Contact>(context, resource, objects) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val row = convertView ?: context.layoutInflater.inflate(resource, null)
        val contact = objects[position]

        val tvName = row.findViewById<TextView>(R.id.tvName)
        val tvPhone = row.findViewById<TextView>(R.id.tvPhone)
        val imgCall = row.findViewById<ImageView>(R.id.imgCall)
        val imgSms = row.findViewById<ImageView>(R.id.imgSms)
        val imgContact = row.findViewById<ImageView>(R.id.imgContact)

        tvName.text = "${position + 1}-${contact.name}"
        tvPhone.text = contact.phone

        // Sử dụng Picasso để load icon
        Picasso.get().load(contact.imgCall).into(imgCall)
        Picasso.get().load(contact.imgSms).into(imgSms)
        Picasso.get().load(contact.imgContact).into(imgContact)

        // Sự kiện call
        imgCall.setOnClickListener {
            val callIntent = Intent(Intent.ACTION_DIAL)
            callIntent.data = Uri.parse("tel:${contact.phone}")
            context.startActivity(callIntent)
        }

        // Sự kiện sms
        imgSms.setOnClickListener {
            val smsIntent = Intent(Intent.ACTION_SENDTO)
            smsIntent.data = Uri.parse("smsto:${contact.phone}")
            context.startActivity(smsIntent)
        }

        // Sự kiện mở danh bạ
        imgContact.setOnClickListener {
            val contactIntent = Intent(Intent.ACTION_VIEW)
            contactIntent.type = ContactsContract.Contacts.CONTENT_TYPE
            context.startActivity(contactIntent)
        }

        return row
    }
}
