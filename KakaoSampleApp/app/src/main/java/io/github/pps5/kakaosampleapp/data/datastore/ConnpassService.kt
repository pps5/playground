package io.github.pps5.kakaosampleapp.data.datastore

import io.github.pps5.kakaosampleapp.data.entity.Feed
import io.github.pps5.kakaosampleapp.data.entity.SearchResponse
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query

@Retention(AnnotationRetention.RUNTIME)
annotation class Xml
@Retention(AnnotationRetention.RUNTIME)
annotation class Json

interface ConnpassService {

    @Json
    @GET("api/v1/event")
    fun searchAsync(@Query("keyword") keyword: String): Deferred<SearchResponse>

    @Xml
    @GET("explore/ja.atom")
    fun getNewArrivalsAsync() : Deferred<Feed>
}