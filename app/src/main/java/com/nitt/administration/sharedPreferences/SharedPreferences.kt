package com.nitt.administration.sharedPreferences

import android.content.Context
import android.content.SharedPreferences

fun getFromSharedPreferences(context: Context, key: String, defaultValue: String = ""): String {
    val sharedPreferences: SharedPreferences = context.getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)
    return sharedPreferences.getString(key, defaultValue) ?: defaultValue
}

