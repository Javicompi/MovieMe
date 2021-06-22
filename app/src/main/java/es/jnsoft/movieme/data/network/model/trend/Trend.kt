package es.jnsoft.movieme.data.network.model.trend


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import es.jnsoft.movieme.data.local.element.Element

@JsonClass(generateAdapter = true)
data class Trend(
    @Json(name = "original_title")
    val originalTitle: String?,
    @Json(name = "poster_path")
    val posterPath: String?,
    val video: Boolean?,
    @Json(name = "vote_average")
    val voteAverage: Double?,
    val overview: String?,
    @Json(name = "release_date")
    val releaseDate: String?,
    val title: String?,
    val adult: Boolean?,
    @Json(name = "backdrop_path")
    val backdropPath: String?,
    @Json(name = "vote_count")
    val voteCount: Int?,
    @Json(name = "genre_ids")
    val genreIds: List<Int>?,
    val id: Int,
    @Json(name = "original_language")
    val originalLanguage: String?,
    val popularity: Double?,
    @Json(name = "media_type")
    val mediaType: String,
    @Json(name = "first_air_date")
    val firstAirDate: String?,
    val name: String?,
    @Json(name = "original_name")
    val originalName: String?,
    @Json(name = "origin_country")
    val originCountry: List<String>?
)

fun Trend.toElement(): Element {
    return Element(
        backdrop = backdropPath ?: "",
        id = mediaType + id.toLong(),
        movieDbId = id.toLong(),
        mediaType = mediaType,
        language = originalLanguage?.uppercase() ?: "",
        overview = overview ?: "",
        poster = posterPath ?: "",
        releaseDate = releaseDate ?: "",
        title = if (!title.isNullOrEmpty()) {
            title
        } else if (!name.isNullOrEmpty()) {
            name
        } else {
            ""
        }
    )
}