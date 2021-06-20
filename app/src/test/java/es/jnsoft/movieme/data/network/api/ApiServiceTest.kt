package es.jnsoft.movieme.data.network.api

import com.haroldadmin.cnradapter.NetworkResponse
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Test

@ExperimentalCoroutinesApi
class ApiServiceTest {

    @Test
    fun trendingAllDayResponseSuccess() = runBlocking {
        val response = ApiServiceObject.apiService.getTrending("all", "day")
        assert(response is NetworkResponse.Success)
        val result = (response as NetworkResponse.Success).body
        assert(result.results.size == 20)
    }

    @Test
    fun trendingAllWeekResponseSuccess() = runBlocking {
        val response = ApiServiceObject.apiService.getTrending("all", "week")
        assert(response is NetworkResponse.Success)
        val result = (response as NetworkResponse.Success).body
        assert(result.results.size == 20)
    }

    @Test
    fun trendingMovieDayResponseSuccess() = runBlocking {
        val response = ApiServiceObject.apiService.getTrending("movie", "day")
        assert(response is NetworkResponse.Success)
        val result = (response as NetworkResponse.Success).body
        assert(result.results.size == 20)
    }

    @Test
    fun trendingTvDayResponseSuccess() = runBlocking {
        val response = ApiServiceObject.apiService.getTrending("tv", "day")
        assert(response is NetworkResponse.Success)
        val result = (response as NetworkResponse.Success).body
        assert(result.results.size == 20)
    }

    @Test
    fun trendingPersonDayResponseSuccess() = runBlocking {
        val response = ApiServiceObject.apiService.getTrending("person", "day")
        assert(response is NetworkResponse.Success)
        val result = (response as NetworkResponse.Success).body
        assert(result.results.size == 20)
    }

    @Test
    fun trendingErrorWrongMediaType() = runBlocking {
        val response = ApiServiceObject.apiService.getTrending("", "")
        assert(response !is NetworkResponse.Success)
        val result = (response as NetworkResponse.ServerError).body
        assert(result?.statusCode!! > 0)
        assert(result.statusMessage.isNotEmpty())
    }

    @Test
    fun searchResponseSuccess() = runBlocking {
        val response = ApiServiceObject.apiService.search(query = "ejercito")
        assert(response is NetworkResponse.Success)
        val result = (response as NetworkResponse.Success).body
        assert(result.results.size == 20)
    }

    @Test
    fun getMovieSuccess() = runBlocking {
        val movieId = 503736L
        val response = ApiServiceObject.apiService.getMovie(
            movieId = movieId
        )
        assert(response is NetworkResponse.Success)
        val result = (response as NetworkResponse.Success).body
        assert(result.id == movieId)
    }

    @Test
    fun getMovieError() = runBlocking {
        val movieId = 1503736L
        val response = ApiServiceObject.apiService.getMovie(
            movieId = movieId
        )
        assert(response is NetworkResponse.ServerError)
    }

    @Test
    fun getTvSuccess() = runBlocking {
        val tvId = 87739L
        val response = ApiServiceObject.apiService.getTv(
            tvId = tvId
        )
        assert(response is NetworkResponse.Success)
        val result = (response as NetworkResponse.Success).body
        assert(result.id == tvId)
    }

    @Test
    fun getTvError() = runBlocking {
        val tvId = 1503736L
        val response = ApiServiceObject.apiService.getTv(
            tvId = tvId
        )
        assert(response is NetworkResponse.ServerError)
    }
}