package io.github.pps5.kakaosampleapp.di

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.tickaroo.tikxml.retrofit.TikXmlConverterFactory
import io.github.pps5.kakaosampleapp.data.datastore.AnnotatedConverterFactory
import io.github.pps5.kakaosampleapp.data.datastore.ConnpassService
import io.github.pps5.kakaosampleapp.data.datastore.Json
import io.github.pps5.kakaosampleapp.data.datastore.Xml
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

private const val BASE_URL = "https://connpass.com/"

val httpModule = module {

    single {
        Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    single {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(
                AnnotatedConverterFactory.Builder()
                    .add(Xml::class, TikXmlConverterFactory.create())
                    .add(Json::class, MoshiConverterFactory.create(get()))
                    .build()
            )
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
            .create(ConnpassService::class.java)
    }

    single { OkHttpClient() }

}
