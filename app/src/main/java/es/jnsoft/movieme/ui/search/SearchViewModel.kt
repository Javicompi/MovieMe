package es.jnsoft.movieme.ui.search

import android.util.Log
import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import es.jnsoft.movieme.data.Repository
import kotlinx.coroutines.*
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val job = Job()
    private val searchScope = CoroutineScope(job + Dispatchers.IO)

    private val searchQuery = MutableLiveData<String>()

    val searchList = searchQuery.switchMap { query ->
        liveData {
            emit(repository.search(query))
        }
    }

    fun search(query: String) {
        job.cancelChildren()
        searchScope.launch {
            delay(1000)
            Log.d("SearchViewModel", "Search: $query")
            searchQuery.postValue(query)
        }
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}