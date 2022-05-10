package com.example.projectexam.data.factory

import com.example.projectexam.BuildConfig
import com.example.projectexam.data.response.latestgame.LatestGameResponse
import com.example.projectexam.data.response.search.SearchResponse
import com.example.projectexam.data.response.toprating.TopRatingResponse
import com.example.projectexam.data.source.HomeDatasource
import io.reactivex.Single

class HomeFactory(private val datasource: HomeDatasource) {

    fun getApiTopRating(page: Long): Single<TopRatingResponse> =
        datasource.getApiTopRating(apiKey = BuildConfig.API_KEY, page = page)

    fun getApiLatest(): Single<LatestGameResponse> =
        datasource.getApiLatest(apiKey = BuildConfig.API_KEY)

    fun getSearch(keyword: String): Single<SearchResponse> =
        datasource.getSearch(apiKey = BuildConfig.API_KEY, keyword = keyword)
}