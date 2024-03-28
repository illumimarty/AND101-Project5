package com.example.project5

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.RequestHeaders
import com.codepath.asynchttpclient.RequestParams
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import com.codepath.asynchttpclient.callback.TextHttpResponseHandler
import okhttp3.Headers

import org.json.JSONArray
import org.w3c.dom.Text
import java.net.URL

class MainActivity : AppCompatActivity() {

    private lateinit var catNameView: TextView
    private lateinit var ratingTextView: TextView
    private lateinit var catImageView: ImageView
    var catImageUrl: String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        catNameView = findViewById(R.id.textView)
        ratingTextView = findViewById(R.id.textView2)
        catImageView = findViewById(R.id.imageView)

        getCatInfo()
    }

    private fun getRandomNumber(max: Int): Int {
        return (0..max).random()
    }

    private fun loadCatImage(url: Any) {
        Glide.with(this)
            .load(url)
            .into(catImageView)
    }

    private fun getCatInfo() {
        val privateKey = "THFyIAr2wwXnlHwNUvU+Eg==rppp9Rub6Bi4NFvV"
        val client = AsyncHttpClient()
        val headers = RequestHeaders()
        val params = RequestParams()
        headers["x-api-key"] = privateKey

        client.get("https://api.api-ninjas.com/v1/cats?min_weight=1", headers, params, object: JsonHttpResponseHandler() {
            override fun onFailure(
                statusCode: Int,
                headers: Headers?,
                errorResponse: String?,
                throwable: Throwable?
            ) {
                errorResponse?.let { unwrappedErrorResponse ->
                    Log.d("cat", "")
                }
            }

            override fun onSuccess(statusCode: Int, headers: Headers?, json: JSON?) {
                json?.let { unwrappedJSON ->

                    val itemCount = json.jsonArray.length()
                    val randomIndex = getRandomNumber(itemCount)
                    val data = json.jsonArray.getJSONObject(randomIndex)
                    val catName = data["name"]
                    val rating = data["family_friendly"]
                    val imageUrl = data["image_link"]

                    catNameView.text = "$catName"
                    ratingTextView.text = "Family Friendliness Rating: $rating"
                    loadCatImage(imageUrl)

                    Log.d("cat", "response successful$data")
                    Log.d("cat", "response successful$catName")
                }
            }
        })
    }
}