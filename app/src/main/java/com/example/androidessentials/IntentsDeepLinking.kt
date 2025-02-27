package com.example.androidessentials

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView

class IntentsDeepLinking : Fragment() {

    val REQUEST_CODE = 1
    private lateinit var imageView: ImageView
    private lateinit var galleryBtn: Button

    private var selectedImageUri: Uri? = null



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_intents_deep_linking, container, false)
        imageView = view.findViewById(R.id.imageView)
        galleryBtn = view.findViewById(R.id.galleryBtn)


        galleryBtn.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent, REQUEST_CODE)
        }

        return view
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK && data != null) {
            selectedImageUri = data.data  // Получаем URI выбранного изображения
            imageView.setImageURI(selectedImageUri)  // Устанавливаем изображение в ImageView
        }
    }
}