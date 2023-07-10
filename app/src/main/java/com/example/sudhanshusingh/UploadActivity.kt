package com.example.sudhanshusingh

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide


class UploadActivity : AppCompatActivity() {

    private lateinit var imageViewPreview: ImageView

    private val pickImageLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        if (uri != null) {
            Glide.with(this)
                .load(uri)
                .into(imageViewPreview)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload)

        imageViewPreview = findViewById(R.id.image_preview)

        val buttonUpload = findViewById<Button>(R.id.button_upload)
        buttonUpload.setOnClickListener {
            openGallery()
        }
    }

    private fun openGallery() {
        pickImageLauncher.launch("image/*")
    }
}

