package es.jnsoft.movieme.ui.detail.movie

import android.util.Log
import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import es.jnsoft.movieme.data.Repository
import es.jnsoft.movieme.data.Result
import es.jnsoft.movieme.data.network.model.movie.toElement
import es.jnsoft.movieme.utils.SingleLiveEvent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class MovieViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    val showSnackBar = SingleLiveEvent<String>()

    val elementId = MutableLiveData(0L)

    val element = elementId.switchMap { id ->
        liveData {
            emitSource(repository.getElement(id))
        }
    }

    val movie = elementId.switchMap { id ->
        liveData {
            emitSource(repository.getMovie(id))
        }
    }

    val isElementInDb = element.switchMap { data ->
        liveData {
            emit(data != null && data.id > 0L)
        }
    }

    val showFab = movie.switchMap { data ->
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
            movie.value?.let {
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