package com.nickrucinski.maps

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import java.io.BufferedReader
import java.io.File
import java.io.FileOutputStream
import java.io.FileReader
import java.io.IOException
import java.io.InputStreamReader

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
                Toast.makeText(context, "There was an error making the request", Toast.LENGTH_LONG)
                    .show()
            }) {
            override fun getParams(): Map<String, String> {
                return params
            }
        }
        queue.add(stringRequest)

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
}