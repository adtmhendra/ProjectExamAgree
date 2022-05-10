package com.example.projectexam.domain.repository

import com.example.projectexam.data.factory.HomeFactory
import com.example.projectexam.domain.HomeParam
import com.example.projectexam.domain.entity.SearchGameEntity
import io.reactivex.Single

class SearchGameRepositoryImpl(
    private val factory: HomeFactory
) : SearchGameRepository {

    override fun getSearch(param: HomeParam, keyword: String): Single<SearchGameEntity> {
        return factory.getSearch(keyword).map { response ->
            SearchGameEntity(
                results = response.results?.map { result ->
                    SearchGameEntity.Result(
                        name = result.name ?: " ",
                        backgroundImage = result.backgroundImage ?: "",
                        rating = result.rating ?: ""
                    )
                }?.toMutableList() ?: mutableListOf()
            )
        }
    }
}