package es.jnsoft.movieme.data.network.model.search


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import es.jnsoft.movieme.data.local.element.Element

@JsonClass(generateAdapter = true)
data class Search(
    val adult: Boolean?,
    @Json(name = "backdrop_path")
    val backdropPath: String?,
    @Json(name = "first_air_date")
    val firstAirDate: String?,
    @Json(name = "genre_ids")
    val genreIds: List<Int>?,
    val id: Int,
    @Json(name = "media_type")
    val mediaType: String,
    val name: String?,
    @Json(name = "origin_country")
    val originCountry: List<String>?,
    @Json(name = "original_language")
    val originalLanguage: String?,
    @Json(name = "original_name")
    val originalName: String?,
    @Json(name = "original_title")
    val originalTitle: String?,
    val overview: String?,
    val popularity: Double?,
    @Json(name = "poster_path")
    val posterPath: String?,
    @Json(name = "release_date")
    val releaseDate: String?,
    val title: String?,
    val video: Boolean?,
    @Json(name = "vote_average")
    val voteAverage: Double?,
    @Json(name = "vote_count")
    val voteCount: Int?
)

fun Search.toElement() : Element {
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