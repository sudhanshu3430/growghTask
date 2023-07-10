package com.example.sudhanshusingh

// FeedActivity.kt
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sudhanshusingh.adapter.FeedAdapter
import com.squareup.picasso.Picasso
import okhttp3.*
import org.json.JSONObject
import java.io.IOException

class FeedActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: FeedAdapter
    private val CACHE_SIZE = (10 * 1024 * 1024).toLong()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feed)

        recyclerView = findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)

        adapter = FeedAdapter(mutableListOf())
        recyclerView.adapter = adapter

        fetchRandomImages()
    }

    private fun fetchRandomImages() {
        val apiKey = "38168498-ac97a2ef23a7d16a044dafce1"
        val randomCategory = getRandomCategory()
        val url = "https://pixabay.com/api/?key=$apiKey&q=$randomCategory&per_page=10"

        val client = OkHttpClient.Builder()
            .cache(Cache(cacheDir, CACHE_SIZE))
            .addInterceptor { chain ->
                val originalRequest = chain.request()
                val requestBuilder = originalRequest.newBuilder()
                    .header("Cache-Control", "no-cache")
                    .header("Pragma", "no-cache")
                val request = requestBuilder.build()
                chain.proceed(request)
            }
            .build()

        val request = Request.Builder()
            .url(url)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                // Handle request failure
            }

            override fun onResponse(call: Call, response: Response) {
                val responseBody = response.body?.string()
                val images = parseImages(responseBody)
                runOnUiThread {
                    adapter.images.clear()
                    adapter.images.addAll(images)
                    adapter.notifyDataSetChanged()
                }
            }
        })
    }

    private fun getRandomCategory(): String {
        val categories = arrayOf("nature", "animals", "food", "travel", "architecture")
        val randomIndex = (0 until categories.size).random()
        return categories[randomIndex]
    }




    private fun parseImages(responseBody: String?): List<String> {
        val images = mutableListOf<String>()
        val json = JSONObject(responseBody)
        val jsonArray = json.getJSONArray("hits")
        for (i in 0 until jsonArray.length()) {
            val item = jsonArray.getJSONObject(i)
            val imageUrl = item.getString("webformatURL")
            images.add(imageUrl)
        }
        return images
    }
}
