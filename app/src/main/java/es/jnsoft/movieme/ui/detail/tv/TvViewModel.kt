package es.jnsoft.movieme.ui.detail.tv

import android.util.Log
import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import es.jnsoft.movieme.data.Repository
import es.jnsoft.movieme.data.Result
import es.jnsoft.movieme.data.network.model.tv.toElement
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TvViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    val elementId = MutableLiveData<Long>()

    val element = elementId.switchMap { id ->
        liveData {
            emitSource(repository.getElement("tv$id"))
        }
    }

    val tv = elementId.switchMap { id ->
        liveData {
            id?.let {
                emitSource(repository.getTv(id))
            }
        }
    }

    val isElementInDb = element.switchMap { data ->
        liveData {
            emit(data != null && data.movieDbId > 0L)
        }
    }

    val showFab = tv.switchMap { data ->
        liveData {
            if (data is Result.Success) {
                emit(true)
            } else {
                emit(false)
            }
        }
    }

    fun onFabClick() {
        viewModelScope.launch {
            tv.value?.let {
                if (it is Result.Success) {
                    val element = it.value.toElement()
                    if (isElementInDb.value == true) {
                        Log.d("MovieViewModel", "Delete element: $element")
                        repository.deleteElement(element)
                    } else {
                        Log.d("MovieViewModel", "Save element: $element")
                        repository.saveElement(element)
                    }
                }
            }
        }
    }
}