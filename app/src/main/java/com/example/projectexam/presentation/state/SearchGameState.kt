package com.example.projectexam.presentation.state

import com.example.projectexam.data.response.search.SearchResponse

sealed class SearchGameState {

    object Loading : SearchGameState()

    data class Success(val response: SearchResponse) : SearchGameState()
    data class Error(val error: Throwable) : SearchGameState()
}