package com.example.fxrates.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.example.fxrates.databinding.ItemRateBinding
import com.example.fxrates.model.TyGia
import com.squareup.picasso.Picasso

class TyGiaAdapter(
    private val activity: Activity,
    private val items: MutableList<TyGia>
) : BaseAdapter() {

    override fun getCount() = items.size
    override fun getItem(position: Int) = items[position]
    override fun getItemId(position: Int) = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val binding: ItemRateBinding
        val view: View

        if (convertView == null) {
            binding = ItemRateBinding.inflate(LayoutInflater.from(activity), parent, false)
            view = binding.root
            view.tag = binding
        } else {
            view = convertView
            binding = view.tag as ItemRateBinding
        }

        val item = getItem(position)
        binding.txtType.text = item.type
        binding.txtMuaTM.text = item.muaTienMat
        binding.txtMuaCK.text = item.muaCK
        binding.txtBanTM.text = item.banTienMat
        binding.txtBanCK.text = item.banCK

        Picasso.get()
            .load(item.imageUrl)
            .fit().centerInside()
            .into(binding.imgHinh)

        return view
    }
}
