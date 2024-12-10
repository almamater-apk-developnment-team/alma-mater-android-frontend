package com.example.journalia_admin_cms

import android.content.Context
import android.content.SharedPreferences

// Function to save data to SharedPreferences
fun saveToSharedPreferences(context: Context, key: String, value: String) {
    val sharedPreferences: SharedPreferences = context.getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()
    editor.putString(key, value)
    editor.apply() // Apply changes asynchronously
}

// Function to retrieve data from SharedPreferences
fun getFromSharedPreferences(context: Context, key: String, defaultValue: String = ""): String {
    val sharedPreferences: SharedPreferences = context.getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)
    return sharedPreferences.getString(key, defaultValue) ?: defaultValue
}
