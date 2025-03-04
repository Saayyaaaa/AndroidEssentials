package com.example.androidessentials.fragments

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import com.example.androidessentials.R

class BroadcastReceiverFragment : Fragment() {

    private lateinit var airplaneModeReceiver: BroadcastReceiver
    private lateinit var statusTextView: TextView
    private lateinit var goBack: ImageButton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_broadcast_receiver, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        statusTextView = view.findViewById(R.id.statusTextView)
        goBack = view.findViewById(R.id.go_back_btn)

        airplaneModeReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                if (intent?.action == Intent.ACTION_AIRPLANE_MODE_CHANGED) {
                    val isAirplaneModeOn = intent.getBooleanExtra("state", false)
                    val message = if (isAirplaneModeOn) "ON" else "OFF"

                    statusTextView.text = "Airplane Mode Status: $message"

                    Toast.makeText(context, message, Toast.LENGTH_LONG).show()
                }

            }
        }

        goBack.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
    }


    override fun onStart() {
        super.onStart()
        val filter = IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED)
        requireContext().registerReceiver(airplaneModeReceiver, filter)
    }

    override fun onStop() {
        super.onStop()
        requireContext().unregisterReceiver(airplaneModeReceiver)
    }
}