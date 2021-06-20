package es.jnsoft.movieme.data.network

import es.jnsoft.movieme.data.network.api.ApiServiceObject
import es.jnsoft.movieme.data.network.model.trend.TrendMediaType
import es.jnsoft.movieme.data.network.model.trend.TrendTimeWindow
import es.jnsoft.movieme.data.succeeded
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Test

@ExperimentalCoroutinesApi
class NetworkDataSourceImplTest {


    private val apiService = ApiServiceObject.apiService

    private val dataSource = NetworkDataSourceImpl(apiService)

    /*@Test
    fun trendingResultSuccess() = runBlocking {
        val result = dataSource.getTrending(TrendMediaType.MOVIE, TrendTimeWindow.DAY)
        assert(result.succeeded)
    }*/

    @Test
    fun searchResultSuccess() = runBlocking {
        val result = dataSource.search("ejercito")
        assert(result.succeeded)
    }

    @Test
    fun pageResult() = runBlocking {
        val page = dataSource.getTrendingPage(TrendMediaType.MOVIE, TrendTimeWindow.DAY, 1)
        assert(page.isSuccessful)
        val results = page.body()?.results
        assert(!results.isNullOrEmpty())
    }
}