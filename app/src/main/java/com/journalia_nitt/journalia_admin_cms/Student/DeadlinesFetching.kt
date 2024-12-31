package com.example.journalia.Student

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import java.util.concurrent.TimeUnit

val logging = HttpLoggingInterceptor().apply {
    level = HttpLoggingInterceptor.Level.BODY
}

val client = OkHttpClient.Builder()
    .addInterceptor(logging)
    .connectTimeout(30, TimeUnit.SECONDS) // Increase connection timeout
    .readTimeout(30, TimeUnit.SECONDS)    // Increase read timeout
    .writeTimeout(30, TimeUnit.SECONDS)   // Increase write timeout
    .build()

val RetrofitInstance = Retrofit.Builder()
    .baseUrl("https://brucewaynegotham007.pythonanywhere.com/")
    .addConverterFactory(GsonConverterFactory.create())
    .client(client)
    .build()

val deadlineApiService = RetrofitInstance.create(DeadlineApiService::class.java)

interface DeadlineApiService {
    @GET("get-deadlines")
    suspend fun getDeadlines(): Response<DeadlineResponse>
}

data class DeadlineResponse(
    val deadlines: List<DeadlineContainer>, // Updated to reflect the array
    val status: String // To match the "status" field
)

data class DeadlineContainer(
    val details: List<Deadline>, // Each object contains a "details" list
    val id: String // Each object contains an "id"
)

data class Deadline(
    val author: String,
    val deadline: String,
    val description: String?,
    val file_url: String?,
    val link1: String?,
    val link2: String?,
    val mode: Int,
    val title: String?
)

class DeadlineViewModel : ViewModel() {

    private val _deadlines = MutableStateFlow<List<Deadline>>(emptyList())
    val deadlines: StateFlow<List<Deadline>> get() = _deadlines

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> get() = _error

    fun fetchDeadlines() {
        viewModelScope.launch {
            try {
                val response = deadlineApiService.getDeadlines() // Call API directly here
                if (response.isSuccessful) {
                    response.body()?.let { deadlineResponse ->
                        // Flatten the deadlines list
                        val allDeadlines = deadlineResponse.deadlines.flatMap { it.details }
                        _deadlines.value = allDeadlines
                        Log.d("DeadlineViewModel", "Fetched deadlines: $allDeadlines")
                    }
                } else {
                    _error.value = "Error: ${response.code()}"
                    Log.d("DeadlineViewModel", "Error: ${error.value.toString()}")
                }
            } catch (e: Exception) {
                _error.value = e.message
                Log.d("DeadlineViewModel", "Error: ${e.toString()}")
            }
        }
    }
}


