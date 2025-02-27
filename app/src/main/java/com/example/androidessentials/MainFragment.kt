package com.example.androidessentials

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.findNavController

class MainFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_main, container, false)

        view.findViewById<Button>(R.id.btn_intents_deep_linking).setOnClickListener {
            view.findNavController().navigate(R.id.action_mainFragment_to_intentsDeepLinking)
        }

        view.findViewById<Button>(R.id.btn_foreground_service).setOnClickListener {
            view.findNavController().navigate(R.id.action_mainFragment_to_foregroundService)
        }

        view.findViewById<Button>(R.id.btn_broadcast_receiver).setOnClickListener {
            view.findNavController().navigate(R.id.action_mainFragment_to_broadcastReceiver)
        }

        view.findViewById<Button>(R.id.btn_content_provider).setOnClickListener {
            view.findNavController().navigate(R.id.action_mainFragment_to_contentProvider)
        }

        return view

    }
}