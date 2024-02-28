package com.nickrucinski.maps

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader

val WEATHER_URL = "https://api.pirateweather.net/forecast/${BuildConfig.WEATHER_API_KEY}/"

class Utils {
    /*
        A function to simplify the volley requests
     */
    public fun getDataFromAPI(
        urlString: String,
        context: Context,
        params: Map<String, String>,
        response: Response.Listener<String>
    ) {
        var sessionKey = ""
        var queue = Volley.newRequestQueue(context)
        var stringRequest = object : StringRequest(
            Method.POST,
            urlString,
            response,
            Response.ErrorListener { error ->
                Log.d("Error", error.toString())
                Toast.makeText(context, "There was an error making the request", Toast.LENGTH_LONG)
                    .show()
            }) {
            override fun getParams(): Map<String, String> {
                return params
            }
        }
        queue.add(stringRequest)
    }

    public fun getDataFromWeatherAPI(
        urlString: String,
        context: Context,
        response: Response.Listener<JSONObject>
    ) {
        var queue = Volley.newRequestQueue(context)
        var jsonRequest = object : JsonObjectRequest(
            urlString,
            response,
            Response.ErrorListener { error ->
                Log.d("Error", error.toString())
                Toast.makeText(context, "There was an error making the request", Toast.LENGTH_LONG)
                    .show()
            }
        ) {

        }
        queue.add(jsonRequest)
    }

    fun ReadJSONFromAssets(context: Context, path: String): String {
        try {
            val file = context.assets.open(path)
            Log.d("JSON", "Found File: $file.",)
            val bufferedReader = BufferedReader(InputStreamReader(file))
            val stringBuilder = StringBuilder()
            bufferedReader.useLines { lines ->
                lines.forEach {
                    stringBuilder.append(it)
                }
            }
            val jsonString = stringBuilder.toString()
            return jsonString
        } catch (e: Exception) {
            Log.d(
                "JSON", "Error reading JSON: $e.",
            )
            e.printStackTrace()
            return ""
        }
    }

    fun createIconMap(): HashMap<String, Int>{
        val iconMap = HashMap<String, Int>()
        iconMap["cloudy"] = R.drawable.cloud_24px
        iconMap["snow"] = R.drawable.weather_snowy_24px
        return iconMap
    }
}