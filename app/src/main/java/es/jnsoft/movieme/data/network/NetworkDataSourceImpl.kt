package es.jnsoft.movieme.data.network

import com.haroldadmin.cnradapter.NetworkResponse
import es.jnsoft.movieme.data.Result
import es.jnsoft.movieme.data.network.api.ApiService
import es.jnsoft.movieme.data.network.model.movie.Movie
import es.jnsoft.movieme.data.network.model.search.Search
import es.jnsoft.movieme.data.network.model.trend.TrendMediaType
import es.jnsoft.movieme.data.network.model.trend.TrendResponse
import es.jnsoft.movieme.data.network.model.trend.TrendTimeWindow
import es.jnsoft.movieme.data.network.model.tv.Tv
import retrofit2.Response
import javax.inject.Inject

class NetworkDataSourceImpl @Inject constructor(
    private val apiService: ApiService
) : NetworkDataSource {

    override suspend fun getTrendingPage(
        mediaType: TrendMediaType,
        timeWindow: TrendTimeWindow,
        page: Int
    ): Response<TrendResponse> {
        return apiService.getTrendingPage(mediaType.value, timeWindow.value, page = page)
    }

    override suspend fun search(query: String): Result<List<Search>> {
        return when (val response = apiService.search(query = query)) {
            is NetworkResponse.Success -> {
                Result.Success(response.body.results)
            }
            is NetworkResponse.ServerError -> {
                Result.Failure(message = response.body?.statusMessage ?: "A server error occurred")
            }
            is NetworkResponse.Error -> {
                Result.Failure(message = response.error.localizedMessage ?: "An error occurred")
            }
            is NetworkResponse.NetworkError -> {
                Result.Failure(
                    message = response.error.localizedMessage ?: "A network error occurred"
                )
            }
            is NetworkResponse.UnknownError -> {
                Result.Failure(message = response.error.localizedMessage ?: "Unknown error")
            }
        }
    }

    override suspend fun getMovie(movieId: Long): Result<Movie> {
        val response = apiService.getMovie(movieId = movieId)
        return convertResponse(response) as Result<Movie>
    }

    override suspend fun getTv(tvId: Long): Result<Tv> {
        val response = apiService.getTv(tvId = tvId)
        return convertResponse(response) as Result<Tv>
    }

    private fun convertResponse(response: NetworkResponse<Any, Any>): Result<Any> {
        return when (response) {
            is NetworkResponse.ServerError -> {
                Result.Failure(message = response.error.localizedMessage ?: "A server error occurred")
            }
            is NetworkResponse.NetworkError -> {
                Result.Failure(message = response.error.localizedMessage ?: "A network error occurred")
            }
            is NetworkResponse.UnknownError -> {
                Result.Failure(message = response.error.localizedMessage ?: "Unknown error")
            }
            is NetworkResponse.Error -> {
                Result.Failure(message = response.error.localizedMessage ?: "An error occurred")
            }
            is NetworkResponse.Success -> {
                Result.Success(response.body)
            }
        }
    }
}