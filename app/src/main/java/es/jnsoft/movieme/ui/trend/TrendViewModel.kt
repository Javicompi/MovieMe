package es.jnsoft.movieme.ui.trend

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import es.jnsoft.movieme.data.Repository
import es.jnsoft.movieme.data.network.model.trend.Trend
import es.jnsoft.movieme.data.network.model.trend.TrendMediaType
import es.jnsoft.movieme.data.network.model.trend.TrendTimeWindow
import es.jnsoft.movieme.utils.SingleLiveEvent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class TrendViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    val showSnackBar = SingleLiveEvent<String>()

    private val movieFilter = MutableStateFlow(TrendTimeWindow.DAY)

    private val tvFilter = MutableStateFlow(TrendTimeWindow.DAY)

    val movieList: Flow<PagingData<Trend>> = movieFilter.flatMapLatest { filter ->
        repository.getTrends(TrendMediaType.MOVIE, filter).flow.cachedIn(viewModelScope)
    }

    val tvList: Flow<PagingData<Trend>> = tvFilter.flatMapLatest { filter ->
        repository.getTrends(TrendMediaType.TV, filter).flow.cachedIn(viewModelScope)
    }

    fun setMovieFilter(filter: TrendTimeWindow) {
        movieFilter.value = filter
    }

    fun setTvFilter(filter: TrendTimeWindow) {
        tvFilter.value = filter
    }
}