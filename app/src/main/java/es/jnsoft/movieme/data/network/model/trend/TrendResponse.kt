package es.jnsoft.movieme.data.network.model.trend


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import es.jnsoft.movieme.data.network.model.trend.Trend

@JsonClass(generateAdapter = true)
data class TrendResponse(
    val page: Int,
    val results: List<Trend>,
    @Json(name = "total_pages")
    val totalPages: Int,
    @Json(name = "total_results")
    val totalResults: Int
)