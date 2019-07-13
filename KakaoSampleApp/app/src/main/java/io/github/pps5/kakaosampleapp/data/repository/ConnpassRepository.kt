package io.github.pps5.kakaosampleapp.data.repository

import io.github.pps5.kakaosampleapp.data.datastore.ConnpassService
import io.github.pps5.kakaosampleapp.data.entity.Entry
import io.github.pps5.kakaosampleapp.data.entity.SearchResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import org.koin.core.KoinComponent
import org.koin.core.inject

class ConnpassRepository : KoinComponent {

    companion object {
        private const val SEARCH_DELAY_IN_MILLIS = 1_000L
    }

    private val connpassService: ConnpassService by inject()

    suspend fun search(
        keyword: String,
        onSuccess: (SearchResponse) -> Unit,
        onFailure: (Throwable) -> Unit
    ) {
        withContext(Dispatchers.IO) {
            runCatching {
                delay(SEARCH_DELAY_IN_MILLIS)
                connpassService.searchAsync(keyword).await()
            }
                .onSuccess(onSuccess)
                .onFailure(onFailure)
        }
    }

    suspend fun getNewArrivals(
        onSuccess: (List<Entry>) -> Unit,
        onFailure: (Throwable) -> Unit
    ) {
        withContext(Dispatchers.IO) {
            runCatching {
                connpassService.getNewArrivalsAsync().await()
            }
                .onSuccess { onSuccess(it.entry) }
                .onFailure(onFailure)
        }
    }
}