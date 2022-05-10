package com.example.projectexam.data.response.search

import com.google.gson.annotations.SerializedName

data class SearchResponse(
    @SerializedName("results")
    val results: List<Search>? = emptyList()
)

data class Search(

    @SerializedName("name")
    val name: String? = null,

    @SerializedName("background_image")
    val backgroundImage: String? = null,

    @SerializedName("rating")
    val rating: String? = null,
)