package es.jnsoft.movieme.ui.detail.movie

import android.util.Log
import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import es.jnsoft.movieme.data.Repository
import es.jnsoft.movieme.data.Result
import es.jnsoft.movieme.data.network.model.movie.toElement
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    val elementId = MutableLiveData<Long>()

    val movie = elementId.switchMap { id ->
        liveData {
            id?.let {
                emitSource(repository.getMovie(id))
            }
        }
    }

    val isElementInDb = elementId.switchMap { id ->
        liveData {
            emitSource(repository.isElementSaved(id))
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