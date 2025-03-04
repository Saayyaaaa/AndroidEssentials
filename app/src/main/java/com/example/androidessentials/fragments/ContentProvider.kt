package com.example.androidessentials.fragments

import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.CalendarContract
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.Manifest
import com.example.androidessentials.R
import com.example.androidessentials.adapters.CalendarEventAdapter
import com.example.androidessentials.models.CalendarEvent

class ContentProvider : Fragment() {

    private lateinit var goBack: ImageButton
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CalendarEventAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_content_provider, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        goBack = view.findViewById(R.id.go_back_btn)
        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        adapter = CalendarEventAdapter()
        recyclerView.adapter = adapter

        goBack.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }

        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_CALENDAR)
            == PackageManager.PERMISSION_GRANTED)
        {
            loadCalendarEvents()
        } else {
            requestPermissions(arrayOf(Manifest.permission.READ_CALENDAR), 100)
        }
    }

    private fun loadCalendarEvents() {
        val events = mutableListOf<CalendarEvent>()
        val projection = arrayOf(CalendarContract.Events.TITLE, CalendarContract.Events.DTSTART)
        val selection = "${CalendarContract.Events.DTSTART} >= ?"
        val selectionArgs = arrayOf(System.currentTimeMillis().toString())

        val cursor = requireContext().contentResolver.query(
            CalendarContract.Events.CONTENT_URI, projection, selection, selectionArgs, null
        )

        cursor?.use {
            while (it.moveToNext()) {
                val title = it.getString(0)
                val date = it.getLong(1)
                events.add(CalendarEvent(title, date))
            }
        }
        adapter.submitList(events)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 100 && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            loadCalendarEvents()
        }
    }

}