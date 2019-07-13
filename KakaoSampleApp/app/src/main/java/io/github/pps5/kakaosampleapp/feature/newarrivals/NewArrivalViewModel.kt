package io.github.pps5.kakaosampleapp.feature.newarrivals

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.github.pps5.kakaosampleapp.common.vo.Resource
import io.github.pps5.kakaosampleapp.data.entity.Entry
import io.github.pps5.kakaosampleapp.data.repository.ConnpassRepository
import io.github.pps5.kakaosampleapp.common.viewmodel.CoroutineScopeViewModel
import kotlinx.coroutines.launch
import org.koin.core.KoinComponent
import org.koin.core.inject

class NewArrivalViewModel : CoroutineScopeViewModel(), KoinComponent {

    private val repository: ConnpassRepository by inject()

    private val _newArrivals = MutableLiveData<Resource<List<Entry>>>()
    val newArrivals: LiveData<Resource<List<Entry>>>
        get() = _newArrivals

    init {
        _newArrivals.postValue(Resource.loading())
        launch {
            repository.getNewArrivals(
                onSuccess = { _newArrivals.postValue(Resource.success(it)) },
                onFailure = { _newArrivals.postValue(Resource.failure()) }
            )
        }
    }
}