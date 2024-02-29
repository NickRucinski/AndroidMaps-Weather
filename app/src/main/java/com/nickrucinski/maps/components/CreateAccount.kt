package com.nickrucinski.maps.components

import android.content.Context
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

@Composable
fun CreateAccountScreen(
    context: Context
){
    val db = Firebase.firestore
    var username by remember { mutableStateOf("32") }
    var password by remember { mutableStateOf("50") }

    Column {
        TextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Username") }
        )

        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") }
        )
        Button(
            onClick = {
                // Create a new user with a first and last name
                val user = hashMapOf(
                    "Username" to username,
                    "Password" to password,
                )

                // Add a new document with a generated ID
                db.collection("Users")
                    .add(user)
                    .addOnSuccessListener { documentReference ->
                        Log.d("Database", "DocumentSnapshot added with ID: ${documentReference.id}")
                    }
                    .addOnFailureListener { e ->
                        Log.w("Database", "Error adding document", e)
                    }
            }
        ) {

        }

    }

}