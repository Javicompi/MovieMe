package es.jnsoft.movieme.data.network

import es.jnsoft.movieme.data.Result
import es.jnsoft.movieme.data.network.model.movie.Movie
import es.jnsoft.movieme.data.network.model.search.Search
import es.jnsoft.movieme.data.network.model.trend.Trend
import es.jnsoft.movieme.data.network.model.trend.TrendMediaType
import es.jnsoft.movieme.data.network.model.trend.TrendResponse
import es.jnsoft.movieme.data.network.model.trend.TrendTimeWindow
import es.jnsoft.movieme.data.network.model.tv.Tv
import retrofit2.Response

interface NetworkDataSource {

    suspend fun search(query: String): Result<List<Search>>

    suspend fun getTrendingPage(
        mediaType: TrendMediaType,
        timeWindow: TrendTimeWindow,
        page: Int
    ): Response<TrendResponse>

    suspend fun getMovie(
        movieId: Long
    ): Result<Movie>

    suspend fun getTv(
        tvId: Long
    ): Result<Tv>
}