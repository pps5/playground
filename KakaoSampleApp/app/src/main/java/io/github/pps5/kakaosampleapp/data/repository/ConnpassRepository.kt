package io.github.pps5.kakaosampleapp.data.repository

import android.content.SharedPreferences
import android.util.Log
import io.github.pps5.kakaosampleapp.data.datastore.AppDatabase
import io.github.pps5.kakaosampleapp.data.datastore.ConnpassService
import io.github.pps5.kakaosampleapp.data.entity.Entry
import io.github.pps5.kakaosampleapp.data.entity.SearchResponse
import io.github.pps5.kakaosampleapp.data.extension.lastEntryCachedDate
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import org.koin.core.KoinComponent
import org.koin.core.inject
import org.threeten.bp.LocalDateTime

class ConnpassRepository : KoinComponent {

    companion object {
        private const val SEARCH_DELAY_IN_MILLIS = 1_000L
        private val TAG = ConnpassRepository::class.java.simpleName
    }

    private val connpassService: ConnpassService by inject()
    private val appDatabase: AppDatabase by inject()
    private val preferences: SharedPreferences by inject()

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
        val cacheExpiresDateTime = preferences.lastEntryCachedDate.plusHours(1)
        withContext(Dispatchers.IO) {
            runCatching {
                val cachedEntries = appDatabase.entryDao().getAll()
                if (LocalDateTime.now().isAfter(cacheExpiresDateTime) || cachedEntries.isEmpty()) {
                    Log.d(TAG, "Fetch from network")
                    val entries = connpassService.getNewArrivalsAsync().await().entry
                    appDatabase.cacheEntries(entries)
                    return@runCatching entries
                }
                Log.d(TAG, "Fetch from cache db")
                return@runCatching cachedEntries
            }
                .onSuccess(onSuccess)
                .onFailure {
                    Log.e(TAG, it.message)
                    onFailure(it)
                }
        }
    }

    private suspend fun AppDatabase.cacheEntries(entries: List<Entry>) = coroutineScope {
        runInTransaction {
            entryDao().let {
                it.deleteAll()
                it.addAll(entries)
            }
            preferences.lastEntryCachedDate = LocalDateTime.now()
        }
    }
}