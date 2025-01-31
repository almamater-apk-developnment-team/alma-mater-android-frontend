package com.nitt.student.sharedPreferences

import android.content.Context
import android.content.SharedPreferences
import com.google.common.reflect.TypeToken
import com.google.gson.Gson
import com.nitt.student.responses.UserInfo

fun saveUserDetails(context: Context, name: String, email: String,role:String) {
    val sharedPreferences: SharedPreferences = context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()
    editor.putString("role",role)
    editor.putString("name", name)
    editor.putString("email", email)
    editor.putBoolean("login_status",true)
    editor.apply()
}
fun saveTokenDetails(context: Context, token: String) {
    val sharedPreferences: SharedPreferences = context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()
    editor.putString("token",token)
    editor.apply()
}
fun getTokenDetails(context: Context): String? {
    val sharedPreferences: SharedPreferences = context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
    val token = sharedPreferences.getString("token", null)
    return token
}
fun getUserDetails(context: Context): UserInfo? {
    val sharedPreferences: SharedPreferences = context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
    val name = sharedPreferences.getString("name", null)
    val email = sharedPreferences.getString("email", null)
    val role = sharedPreferences.getString("role", null)
    val loginStatus = sharedPreferences.getBoolean("login_status",false)
    return if (name != null && email != null && role != null) {
        UserInfo(name, email,loginStatus,role)
    } else {
        null
    }
}
fun clearUserDetails(context: Context) {
    val sharedPreferences: SharedPreferences = context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()
    editor.clear()
    editor.putBoolean("login_status", false)
    editor.apply()
}
fun saveArrayToSharedPreferences(context : Context, complaint : String , key : String) {
    val sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()
    val gson = Gson()
    val array = getArrayFromSharedPreferences(context,key)
    array.add(complaint)
    val json = gson.toJson(array)
    editor.putString(key, json)
    editor.apply()
}
fun getArrayFromSharedPreferences(context : Context, key : String): MutableList<String> {
    val sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
    val gson = Gson()
    val json = sharedPreferences.getString(key, null)
    return if (json != null) {
        val type = object : TypeToken<MutableList<String>>() {}.type
        gson.fromJson(json, type)
    } else {
        mutableListOf()
    }
}

fun saveUserLoginToken(context: Context, token: String) {
    val sharedPreferences: SharedPreferences = context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()
    editor.putString("token", token)
    editor.apply()
}

fun getUserLoginToken(context: Context): String? {
    val sharedPreferences: SharedPreferences = context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
    return sharedPreferences.getString("token", null)
}