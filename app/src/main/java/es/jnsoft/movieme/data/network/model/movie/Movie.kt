package es.jnsoft.movieme.data.network.model.movie


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import es.jnsoft.movieme.data.local.element.Element
import es.jnsoft.movieme.data.network.model.common.Genre

@JsonClass(generateAdapter = true)
data class Movie(
    val adult: Boolean = false,
    @Json(name = "backdrop_path")
    val backdropPath: String? = "",
    val budget: Int? = 0,
    val genres: List<Genre>? = listOf(),
    val homepage: String? = "",
    val id: Long,
    @Json(name = "imdb_id")
    val imdbId: String? = "",
    @Json(name = "original_language")
    val originalLanguage: String? = "",
    @Json(name = "original_title")
    val originalTitle: String? = "",
    val overview: String? = "",
    val popularity: Double? = 0.0,
    @Json(name = "poster_path")
    val posterPath: String? = "",
    @Json(name = "release_date")
    val releaseDate: String? = "",
    val revenue: Int? = 0,
    val status: String? = "",
    val tagline: String? = "",
    val title: String? = "",
    val video: Boolean? = false,
    @Json(name = "vote_average")
    val voteAverage: Double? = 0.0,
    @Json(name = "vote_count")
    val voteCount: Int? = 0
)

fun Movie.toElement(): Element {
    return Element(
        backdrop = backdropPath ?: "",
        id = "movie$id",
        language = originalLanguage?.uppercase() ?: "",
        movieDbId = id,
        mediaType = "movie",
        overview = overview ?: "",
        poster = posterPath ?: "",
        releaseDate = releaseDate ?: "",
        title = title ?: ""
    )
}