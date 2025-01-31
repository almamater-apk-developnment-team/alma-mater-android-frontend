package com.nitt.administration

import com.nitt.administration.services.FileUpload
import com.nitt.administration.services.Login
import com.nitt.administration.services.Secret
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private val retrofit= Retrofit.Builder()
    .baseUrl("https://journaliaadmin.vercel.app/")
    .addConverterFactory(GsonConverterFactory.create())
    .build()
val FileUploadClient= retrofit.create(FileUpload::class.java)
val LoginClient = retrofit.create(Login::class.java)
val SecretClient = retrofit.create(Secret::class.java)

