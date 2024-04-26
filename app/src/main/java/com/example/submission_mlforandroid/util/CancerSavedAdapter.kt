package com.example.submission_mlforandroid.util

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.submission_mlforandroid.R
import com.example.submission_mlforandroid.data.database.CancerSaved

class CancerSavedAdapter(var cancerSavedList : List<CancerSaved>, private val context: Context ): RecyclerView.Adapter<CancerSavedAdapter.UserViewHolder>() {
    inner class UserViewHolder(itemView : View): RecyclerView.ViewHolder(itemView) {
        private val img:ImageView = itemView.findViewById(R.id.iv_cancer)
        private val tvDetect:TextView = itemView.findViewById(R.id.tv_detection)
        private val tvScore:TextView = itemView.findViewById(R.id.tv_score)

        fun bind(cancerSaved : CancerSaved) {
            val imageUri = cancerSaved.imageUrl?.let { Uri.parse(it) }
            if (imageUri != null) {

                Glide.with(context).load(imageUri).into(img)
            } else {
                img.setImageResource(R.drawable.image)
            }
            tvDetect.text = cancerSaved.resultDetection
            tvScore.text = cancerSaved.confidenceScore?.toString() ?: "Unknown"
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view =LayoutInflater.from(parent.context).inflate(R.layout.item_cancer_saved, parent, false)
        return UserViewHolder(view)
    }

    override fun getItemCount(): Int {
        return cancerSavedList.size
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val cancer = cancerSavedList[position]
        holder.bind(cancer)
    }
}