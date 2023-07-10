package com.example.sudhanshusingh

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContentView(R.layout.activity_home)


        val feedButton: View = findViewById(R.id.feed_button)
        feedButton.setOnClickListener {
            // Handle feed button click
            openFeedScreen()
        }

        val uploadButton: View = findViewById(R.id.upload_button)
        uploadButton.setOnClickListener {
            // Handle upload button click
            openUploadScreen()
        }
    }

    private fun openFeedScreen() {
        val intent = Intent(this, FeedActivity::class.java)
        startActivity(intent)
    }

    private fun openUploadScreen() {
        val intent = Intent(this, UploadActivity::class.java)
        startActivity(intent)
    }
}