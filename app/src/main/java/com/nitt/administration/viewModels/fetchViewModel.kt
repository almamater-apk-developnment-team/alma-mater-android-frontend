package com.nitt.administration.viewModels

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nitt.administration.FileUploadClient
import com.nitt.administration.response.ForEachUser
import kotlinx.coroutines.launch

class AdminDetailsViewModel : ViewModel() {
    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading
    private val _loadingStatus = mutableStateOf("")
    val loadingStatus: State<String> = _loadingStatus
    private val _detailsList = mutableStateListOf<ForEachUser>()
    val detailsList: List<ForEachUser> = _detailsList
    fun fetchAdminDetails() {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                val response = FileUploadClient.fetchAdminDetails()
                if (response.isSuccessful && response.body() != null) {
                    _detailsList.clear()
                    val fetchedData = response.body()!!
                    _detailsList.addAll(fetchedData.data)
                    _loadingStatus.value = "Loaded Successfully"
                    _isLoading.value = false
                } else {
                    val errorBody = response.errorBody()?.string()
                    _loadingStatus.value = "Data Loading Failed"
                    Log.e("AdminDetails", "Fetch failed: ${response.code()}, Error: $errorBody")
                }
            } catch (e: Exception) {
                _loadingStatus.value = "Fetch Failed"
                Log.e("AdminDetails", "Error fetching details", e)
            }
        }
    }
}
