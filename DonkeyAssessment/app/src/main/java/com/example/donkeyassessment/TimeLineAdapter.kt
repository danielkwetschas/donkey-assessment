package com.example.donkeyassessment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.donkeyassessment.data.TimeLineItem

class TimeLineAdapter: ListAdapter<TimeLineItem, TimeLineAdapter.TimeLineViewHolder>(DiffCallback) {
    class TimeLineViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val groupNameTextView: TextView = itemView.findViewById(R.id.groupNameTextView)
        val contentTextView: TextView = itemView.findViewById(R.id.contentTextView)
        val contentImageView: ImageView = itemView.findViewById(R.id.contentImageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimeLineViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_timeline, parent, false)
        return TimeLineViewHolder(view)
    }

    override fun onBindViewHolder(holder: TimeLineViewHolder, position: Int) {
        val item = getItem(position)
        holder.groupNameTextView.text = item.groupName
        holder.contentTextView.text = item.content
        holder.contentImageView.isVisible = item.imgURL != null

        if(item.imgURL != null) {
            holder.contentImageView.load(item.imgURL)
        } else {
            holder.contentImageView.setImageDrawable(null)
        }
    }

    companion object {
        private val DiffCallback =
            object : DiffUtil.ItemCallback<TimeLineItem>() {
                override fun areItemsTheSame(
                    oldItem: TimeLineItem,
                    newItem: TimeLineItem
                ): Boolean {
                    return oldItem.id == newItem.id
                }

                override fun areContentsTheSame(
                    oldItem: TimeLineItem,
                    newItem: TimeLineItem
                ): Boolean {
                    return oldItem == newItem
                }
            }
    }
}