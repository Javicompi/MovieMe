package es.jnsoft.movieme.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.liveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import es.jnsoft.movieme.data.local.LocalDataSourceImpl
import es.jnsoft.movieme.data.local.element.Element
import es.jnsoft.movieme.data.local.element.toMovie
import es.jnsoft.movieme.data.local.element.toTv
import es.jnsoft.movieme.data.network.NetworkDataSourceImpl
import es.jnsoft.movieme.data.network.TrendingPagingSource
import es.jnsoft.movieme.data.network.model.movie.Movie
import es.jnsoft.movieme.data.network.model.search.Search
import es.jnsoft.movieme.data.network.model.trend.TrendMediaType
import es.jnsoft.movieme.data.network.model.trend.TrendTimeWindow
import es.jnsoft.movieme.data.network.model.tv.Tv
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class Repository @Inject constructor(
    private val networkDataSource: NetworkDataSourceImpl,
    private val localDataSource: LocalDataSourceImpl
) {

    fun getTrends(
        mediaType: TrendMediaType,
        timeWindow: TrendTimeWindow
    ) = Pager(PagingConfig(
        pageSize = 20,
        maxSize = 100
    )) {
        TrendingPagingSource(networkDataSource, mediaType, timeWindow)
    }

    suspend fun search(query: String): Result<List<Search>> = withContext(Dispatchers.IO) {
        val result = networkDataSource.search(query)
        if (result is Result.Success && result.value.isNotEmpty()) {
            val moviesAndTvs: List<Search> = result.value.filter {
                it.mediaType == "movie" || it.mediaType == "tv"
            }
            if (moviesAndTvs.isNotEmpty()) {
                val sorted: List<Search> = moviesAndTvs.sortedByDescending {
                    it.releaseDate ?: it.firstAirDate
                }
                return@withContext Result.Success(sorted)
            }
            return@withContext Result.Success(moviesAndTvs)
        }
        return@withContext result
    }

    suspend fun getFavourites(): LiveData<List<Element>> = withContext(Dispatchers.IO) {
        return@withContext localDataSource.getElements()
    }

    suspend fun getElement(id: String): LiveData<Element> = withContext(Dispatchers.IO) {
        return@withContext localDataSource.getElement(id)
    }

    suspend fun getMovie(id: Long): LiveData<Result<Movie>> = withContext(Dispatchers.IO) {
        return@withContext liveData {
            val result = MediatorLiveData<Result<Movie>>()
            val dbSource = localDataSource.getElement("movie$id")
            val networkSource = networkDataSource.getMovie(id)
            result.addSource(dbSource) { data ->
                result.removeSource(dbSource)
                if (data != null && data.id.isNotEmpty()) {
                    result.value = Result.Success(data.toMovie())
                }
                if (networkSource.succeeded) {
                    result.value = networkSource
                }
            }
            emitSource(result)
        }
    }

    suspend fun getTv(id: Long): LiveData<Result<Tv>> = withContext(Dispatchers.IO) {
        return@withContext liveData {
            val result = MediatorLiveData<Result<Tv>>()
            val dbSource = localDataSource.getElement("tv$id")
            val networkSource = networkDataSource.getTv(id)
            result.addSource(dbSource) { data ->
                result.removeSource(dbSource)
                if (data != null && data.id.isNotEmpty()) {
                    result.value = Result.Success(data.toTv())
                }
                if (networkSource.succeeded) {
                    result.value = networkSource
                }
            }
            emitSource(result)
        }
    }

    suspend fun isElementSaved(id: String): LiveData<Boolean> = withContext(Dispatchers.IO) {
        return@withContext localDataSource.getElementId(id)
    }

    suspend fun saveElement(element: Element) = withContext(Dispatchers.IO) {
        localDataSource.insertElement(element)
    }

    suspend fun deleteElement(element: Element) = withContext(Dispatchers.IO) {
        localDataSource.deleteElement(element)
    }
}