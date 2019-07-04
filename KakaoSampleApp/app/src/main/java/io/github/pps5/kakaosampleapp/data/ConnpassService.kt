package io.github.pps5.kakaosampleapp.data

import io.github.pps5.kakaosampleapp.data.entity.SearchResponse
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query

interface ConnpassService {

    @GET("event")
    fun searchAsync(@Query("keyword") keyword: String): Deferred<SearchResponse>
}