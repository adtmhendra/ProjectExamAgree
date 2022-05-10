package com.example.projectexam.presentation.state

import com.example.projectexam.data.response.toprating.TopRatingResponse

sealed class TopRatingState {

    object Loading : TopRatingState()

    data class Success(val response: TopRatingResponse) : TopRatingState()
    data class Error(val error: Throwable) : TopRatingState()
}
