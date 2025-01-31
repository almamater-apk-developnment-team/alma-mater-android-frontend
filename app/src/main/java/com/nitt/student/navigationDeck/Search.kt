package com.nitt.student.navigationDeck

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class Search:ViewModel() {
    private var _searchQuery = mutableStateOf("")
    var searchQuery: State<String> = _searchQuery

    fun updateSearchQuery(newQuery: String) {
        _searchQuery.value = newQuery
    }
}