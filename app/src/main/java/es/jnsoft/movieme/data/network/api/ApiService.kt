package es.jnsoft.movieme.data.network.api

import com.haroldadmin.cnradapter.NetworkResponse
import com.haroldadmin.cnradapter.NetworkResponseAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import es.jnsoft.movieme.data.network.model.ErrorResponse
import es.jnsoft.movieme.data.network.model.movie.Movie
import es.jnsoft.movieme.data.network.model.trend.TrendResponse
import es.jnsoft.movieme.data.network.model.search.SearchResponse
import es.jnsoft.movieme.data.network.model.tv.Tv
import es.jnsoft.movieme.utils.Constants
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.*

interface ApiService {

    @GET("trending/{media_type}/{time_window}?api_key=${Constants.API_KEY}")
    suspend fun getTrending(
        @Path("media_type") mediaType: String? = "movie",
        @Path("time_window") timeWindow: String? = "day",
        @Query("language") language: String = Locale.getDefault().language,
        @Query("region") region: String = Locale.getDefault().country,
        @Query("page") page: Int? = 1
    ): NetworkResponse<TrendResponse, ErrorResponse>

    @GET("trending/{media_type}/{time_window}?api_key=${Constants.API_KEY}")
    suspend fun getTrendingPage(
        @Path("media_type") mediaType: String? = "movie",
        @Path("time_window") timeWindow: String? = "day",
        @Query("language") language: String = Locale.getDefault().language,
        @Query("region") region: String = Locale.getDefault().country,
        @Query("page") page: Int
    ): Response<TrendResponse>

    @GET("search/multi?api_key=${Constants.API_KEY}")
    suspend fun search(
        @Query("language") language: String = Locale.getDefault().language,
        @Query("region") region: String = Locale.getDefault().country,
        @Query("page") page: Int = 1,
        @Query("query") query: String
    ): NetworkResponse<SearchResponse, ErrorResponse>

    @GET("movie/{movie_id}?api_key=${Constants.API_KEY}")
    suspend fun getMovie(
        @Path("movie_id") movieId: Long,
        @Query("language") language: String = Locale.getDefault().language,
        @Query("region") region: String = Locale.getDefault().country,
    ): NetworkResponse<Movie, ErrorResponse>

    @GET("tv/{tv_id}?api_key=${Constants.API_KEY}")
    suspend fun getTv(
        @Path("tv_id") tvId: Long,
        @Query("language") language: String = Locale.getDefault().language,
        @Query("region") region: String = Locale.getDefault().country,
    ): NetworkResponse<Tv, ErrorResponse>
}

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .baseUrl(Constants.BASE_URL)
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(NetworkResponseAdapterFactory())
    .build()

object ApiServiceObject {
    val apiService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}