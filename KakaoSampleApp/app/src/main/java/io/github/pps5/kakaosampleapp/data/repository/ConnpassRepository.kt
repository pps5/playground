package io.github.pps5.kakaosampleapp.data.repository

import io.github.pps5.kakaosampleapp.data.ConnpassService
import io.github.pps5.kakaosampleapp.data.entity.SearchResponse
import io.github.pps5.kakaosampleapp.vo.Result
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import org.koin.core.KoinComponent
import org.koin.core.inject

class ConnpassRepository(private val scope: CoroutineScope) : KoinComponent {

    companion object {
        private const val SEARCH_DELAY = 1_000L
    }
    private val connpassService: ConnpassService by inject()

    fun search(keyword: String): Channel<Result<SearchResponse>> {
        val channel = Channel<Result<SearchResponse>>()
        scope.launch(Dispatchers.IO) {
            runCatching {
                Thread.sleep(SEARCH_DELAY)
                connpassService.searchAsync(keyword).await()
            }
                .onSuccess { channel.send(Result.Success(it)) }
                .onFailure { channel.send(Result.Failure(it)) }
        }
        return channel
    }

}