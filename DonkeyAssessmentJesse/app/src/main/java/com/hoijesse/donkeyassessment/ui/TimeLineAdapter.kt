package com.hoijesse.donkeyassessment.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil3.load
import com.hoijesse.donkeyassessment.R
import com.hoijesse.donkeyassessment.data.TimeLineEntry

class TimeLineAdapter: ListAdapter<TimeLineEntry, TimeLineAdapter.TimeLineViewHolder>(DiffCallback) {
    class TimeLineViewHolder(entryView: View) : RecyclerView.ViewHolder(entryView) {
        val entryInGroup: TextView = entryView.findViewById(R.id.entryInGroup)
        val entryText: TextView = entryView.findViewById(R.id.entryText)
        val entryImage: ImageView = entryView.findViewById(R.id.entryImage)
        val entrySenderName: TextView = entryView.findViewById(R.id.entrySenderName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimeLineViewHolder {
        val entryView = LayoutInflater.from(parent.context).inflate(R.layout.timeline_entry, parent, false)
        return TimeLineViewHolder(entryView)
    }

    override fun onBindViewHolder(holder: TimeLineViewHolder, position: Int) {
        val entry = getItem(position)
        holder.entryInGroup.text = entry.inGroup
        holder.entryText.text = entry.text
        if (entry.image != null) {
            holder.entryImage.load(entry.image)
        } else {
            holder.entryImage.setImageDrawable(null)
            holder.entryImage.isVisible = false
        }
        holder.entrySenderName.text = entry.senderName
    }

    internal fun getEntryAt(position: Int) = getItem(position)

    companion object {
        internal val DiffCallback = object : DiffUtil.ItemCallback<TimeLineEntry>() {
            override fun areItemsTheSame(old: TimeLineEntry, new: TimeLineEntry): Boolean {return old.id == new.id}
            override fun areContentsTheSame(old: TimeLineEntry, new: TimeLineEntry): Boolean {return old == new}
        }
    }
}