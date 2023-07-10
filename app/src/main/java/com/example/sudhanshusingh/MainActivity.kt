package com.example.sudhanshusingh

// MainActivity.kt
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private val REDIRECTION_FLAG = "redirection_flag"

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

        // Check if the redirection flag is set
        val isRedirectionOccurred = sharedPreferences.getBoolean(REDIRECTION_FLAG, false)

        if (isRedirectionOccurred) {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            return
        }



        val readyButton: View = findViewById(R.id.ready_button)
        readyButton.setOnClickListener {

            // Open the HomeActivity
            sharedPreferences.edit().putBoolean(REDIRECTION_FLAG, true).apply()
            val intent = Intent(this, HomeActivity::class.java)

            startActivity(intent)
            finishAffinity()


        }

        val skipButton: View = findViewById(R.id.skip_button)
        skipButton.setOnClickListener {

            finishAffinity()
        }
    }

    private fun openHomeScreen() {
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
        finish()
    }
}
