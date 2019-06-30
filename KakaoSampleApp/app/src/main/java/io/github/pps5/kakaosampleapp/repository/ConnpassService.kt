package io.github.pps5.kakaosampleapp.repository

import io.github.pps5.kakaosampleapp.repository.entity.SearchResponse
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query

interface ConnpassService {

    @GET("event")
    fun search(@Query("keyword") keyword: String): Deferred<SearchResponse>
}