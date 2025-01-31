package com.nitt.student.services

import com.nitt.administration.response.AdminPost
import com.nitt.student.responses.BookMarkFetch
import com.nitt.student.responses.DeadlineRequest
import com.nitt.student.responses.DeadlineResponse
import com.nitt.student.responses.LoginRequest
import com.nitt.student.responses.TokenResponse
import com.nitt.student.responses.ZResponse
import com.nitt.student.responses.userUploadResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface StudentApiService {
    @POST("/access_token")
    fun getToken(@Body loginRequest: LoginRequest): Call<TokenResponse>
    @POST("/post_deadline")
    fun postDeadline(
        @Header("authorization") token: String,
        @Body deadlineRequest: DeadlineRequest
    ): Call<ZResponse>
    @GET("/calendar_details")
    suspend fun fetchData(
        @Header("authorization") token: String,
    ): Map<String, String>
}

interface handleBookMark{
    @POST("/student/bookmark/")
    suspend fun bookMark(@Body bookMark: AdminPost,@Header("authorization") token: String): userUploadResponse

    @GET("/student/fetchBookmark/")
    suspend fun getAllBook(@Header("authorization") token: String): BookMarkFetch

    @GET("/student/get-token/")
    suspend fun getStudentToken(@Query("rollno") rollno: String): TokenResponse
}
interface DeadlineApiService {
    @GET("get-deadlines")
    suspend fun getDeadlines(): Response<DeadlineResponse>
}



