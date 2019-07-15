package io.github.pps5.kakaosampleapp.feature.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.github.pps5.kakaosampleapp.common.viewmodel.CoroutineScopeViewModel
import io.github.pps5.kakaosampleapp.common.vo.Resource
import io.github.pps5.kakaosampleapp.data.entity.Event
import io.github.pps5.kakaosampleapp.data.repository.ConnpassRepository
import kotlinx.coroutines.launch
import org.koin.core.KoinComponent
import org.koin.core.inject
import org.koin.core.parameter.parametersOf

class SearchViewModel(private val query: String) : CoroutineScopeViewModel(), KoinComponent {

    private val repository: ConnpassRepository by inject { parametersOf(this) }

    private val _events = MutableLiveData<Resource<List<Event>>>()
    val events: LiveData<Resource<List<Event>>>
        get() = _events

    init {
        _events.postValue(Resource.loading())
        launch {
            repository.search(query,
                onSuccess = { _events.postValue(Resource.success(it.events)) },
                onFailure = { _events.postValue(Resource.failure()) })
        }
    }
}