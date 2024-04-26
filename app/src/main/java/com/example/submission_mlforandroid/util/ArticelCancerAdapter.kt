package com.example.submission_mlforandroid.util

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.submission_mlforandroid.R
import com.example.submission_mlforandroid.data.response.ArticlesItem

class ArticelCancerAdapter(var articelList : List<ArticlesItem>, private val context: Context) : RecyclerView.Adapter<ArticelCancerAdapter.ArticelViewHolder>() {
    inner class ArticelViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        private val img : ImageView = itemView.findViewById(R.id.iv_articel)
        private val judul : TextView = itemView.findViewById(R.id.tv_judul)
        private val deskripsi : TextView = itemView.findViewById(R.id.tv_desc)

        fun bind(articel : ArticlesItem) {
            val imgArticel = articel.urlToImage

            if (imgArticel != null) {
                Glide.with(context).load(articel.urlToImage).into(img)
            } else {
                img.setImageResource(R.drawable.image)
            }

            judul.text = articel.title ?: "Lorem ipsum dolor sit amet, consectetur adipiscing elit. "
            deskripsi.text = articel.description ?: "Fusce molestie ligula nec arcu sollicitudin, quis placerat odio condimentum. In sed nisl auctor, interdum urna maximus, molestie urna."
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticelViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_articel, parent, false)
        return ArticelViewHolder(view)
    }

    override fun getItemCount(): Int {
        return articelList.size
    }

    override fun onBindViewHolder(holder: ArticelViewHolder, position: Int) {
        val articel =articelList[position]
        holder.bind(articel)
    }
}