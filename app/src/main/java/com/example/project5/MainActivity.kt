package com.example.project5

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.RequestHeaders
import com.codepath.asynchttpclient.RequestParams
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import com.codepath.asynchttpclient.callback.TextHttpResponseHandler
import okhttp3.Headers

import org.json.JSONArray
import org.w3c.dom.Text

class MainActivity : AppCompatActivity() {

    private lateinit var catNameView: TextView
    private lateinit var ratingTextView: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        catNameView = findViewById(R.id.textView)
        ratingTextView = findViewById(R.id.textView2)


        getCatInfo()
    }

    private fun getCatInfo() {
        val privateKey = "THFyIAr2wwXnlHwNUvU+Eg==rppp9Rub6Bi4NFvV"
        val client = AsyncHttpClient()
        val headers = RequestHeaders()
        val params = RequestParams()
        headers["x-api-key"] = privateKey

//        headers.put("x-api-key", privateKey)


        client.get("https://api.api-ninjas.com/v1/cats?name=abyssinian", headers, params, object: JsonHttpResponseHandler() {
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
                    val data = json.jsonArray.getJSONObject(0)
                    val catName = data["name"]
                    val rating = data["family_friendly"]
                    catNameView.text = "$catName"
                    ratingTextView.text = "Family Friendliness Rating: $rating"

                    Log.d("cat", "response successful$data")
                    Log.d("cat", "response successful$catName")
                }
            }
        })
    }
}