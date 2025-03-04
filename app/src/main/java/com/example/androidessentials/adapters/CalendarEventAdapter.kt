package com.example.androidessentials.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.androidessentials.R
import com.example.androidessentials.models.CalendarEvent
import java.text.SimpleDateFormat
import java.util.*

class CalendarEventAdapter : ListAdapter<CalendarEvent, CalendarEventAdapter.ViewHolder>(
    object : DiffUtil.ItemCallback<CalendarEvent>() {
        override fun areItemsTheSame(oldItem: CalendarEvent, newItem: CalendarEvent) = oldItem.date == newItem.date
        override fun areContentsTheSame(oldItem: CalendarEvent, newItem: CalendarEvent) = oldItem == newItem
    }
) {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val titleView: TextView = view.findViewById(R.id.eventTitle)
        private val dateView: TextView = view.findViewById(R.id.eventDate)

        fun bind(event: CalendarEvent) {
            titleView.text = event.title
            dateView.text = SimpleDateFormat("dd MMM yyyy HH:mm", Locale.getDefault()).format(Date(event.date))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_calendar_event, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}
