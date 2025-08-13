package com.example.news.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.example.news.Article
import com.example.news.R
import com.example.news.databinding.ItemArticleBinding
import com.squareup.picasso.Picasso

class ArticleAdapter(
    context: Context,
    private val items: MutableList<Article>
) : ArrayAdapter<Article>(context, R.layout.item_article, items) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val binding: ItemArticleBinding
        val row: View

        if (convertView == null) {
            binding = ItemArticleBinding.inflate(LayoutInflater.from(context), parent, false)
            row = binding.root
            row.tag = binding
        } else {
            row = convertView
            binding = row.tag as ItemArticleBinding
        }

        val item = items[position]
        binding.txtTitle.text = item.title
        binding.txtInfo.text = item.summary

        if (!item.imageUrl.isNullOrBlank()) {
            Picasso.get()
                .load(item.imageUrl)
                .placeholder(android.R.drawable.ic_menu_report_image)
                .fit()
                .centerCrop()
                .into(binding.imgView)
        } else {
            binding.imgView.setImageResource(android.R.drawable.ic_menu_report_image)
        }

        return row
    }
}
