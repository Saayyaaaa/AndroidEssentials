package com.example.androidessentials

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import com.example.androidessentials.services.MusicService

class ForegroundService : Fragment() {

    private lateinit var btnPlayStop: ImageButton
    private var isPlaying = false
    private lateinit var goBack: ImageButton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(
            R.layout.fragment_foreground_service, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnPrevious = view.findViewById<ImageButton>(R.id.btnPrevious)
        btnPlayStop = view.findViewById(R.id.btnPlayStop)
        val btnNext = view.findViewById<ImageButton>(R.id.btnNext)
        goBack = view.findViewById(R.id.go_back_btn)

        btnPlayStop.setOnClickListener {
            togglePlayStop()
        }

        btnPrevious.setOnClickListener {
            sendMusicAction("ACTION_PREVIOUS")
            Toast.makeText(requireContext(), "Предыдущий трек", Toast.LENGTH_SHORT).show()
        }

        btnNext.setOnClickListener {
            sendMusicAction("ACTION_NEXT")
            Toast.makeText(requireContext(), "Следующий трек", Toast.LENGTH_SHORT).show()
        }

        goBack.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
    }

    private fun togglePlayStop() {
        isPlaying = !isPlaying
        val action = if (isPlaying) "ACTION_PLAY" else "ACTION_STOP"

        // Меняем иконку вместо текста
        val icon = if (isPlaying) R.drawable.stop else R.drawable.play
        btnPlayStop.setImageResource(icon)

        val intent = Intent(requireContext(), MusicService::class.java).apply {
            this.action = action
        }
        
        requireContext().startService(intent)
    }

    private fun sendMusicAction(action: String) {
        val intent = Intent(requireContext(), MusicService::class.java).apply {
            this.action = action
        }
        requireContext().startService(intent)
    }
}