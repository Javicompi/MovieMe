package es.jnsoft.movieme.ui.favourites

import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import es.jnsoft.movieme.data.Repository
import es.jnsoft.movieme.data.Result
import es.jnsoft.movieme.data.local.element.Element
import es.jnsoft.movieme.utils.SingleLiveEvent
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavouritesViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    val showSnackBar = SingleLiveEvent<String>()

    val favourites = MediatorLiveData<List<Element>>()

    init {
        viewModelScope.launch {
            favourites.addSource(repository.getFavourites()) { list ->
                favourites.value = list
            }
        }
    }
}