package com.example.myapplication

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private val retrofit=Retrofit.Builder().baseUrl("http://10.0.2.2:5000/").addConverterFactory(GsonConverterFactory.create()).build()
val Client= retrofit.create(fileUpload::class.java)

interface fileUpload{

}