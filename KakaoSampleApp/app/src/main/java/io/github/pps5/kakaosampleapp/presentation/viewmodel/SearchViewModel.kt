package io.github.pps5.kakaosampleapp.presentation.viewmodel

import android.os.Handler
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import io.github.pps5.kakaosampleapp.data.entity.SearchResponse
import io.github.pps5.kakaosampleapp.data.repository.ConnpassRepository
import kotlinx.coroutines.*
import org.koin.core.KoinComponent
import org.koin.core.inject
import org.koin.core.parameter.parametersOf

class SearchViewModel : ViewModel(), KoinComponent, CoroutineScope {

    private val job = Job()
    override val coroutineContext = Dispatchers.Main + job
    private val repository: ConnpassRepository by inject { parametersOf(this) }

    private val query = MutableLiveData<String>()

    val response: LiveData<SearchResponse> = Transformations.switchMap(query) {
        job.cancelChildren()
        val result = MutableLiveData<SearchResponse>()
        launch {
            repository.search(it,
                onSuccess = { Log.d("dbg", it.toString()) },
                onFailure = { it.printStackTrace() })
        }
        result
    }

    init {
        Handler().postDelayed({
            query.postValue("shibuya apk")
        }, 1000)
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}