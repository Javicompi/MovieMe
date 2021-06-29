package es.jnsoft.movieme.data.network.model.tv


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import es.jnsoft.movieme.data.local.element.Element
import es.jnsoft.movieme.data.network.model.common.Genre

@JsonClass(generateAdapter = true)
data class Tv(
    @Json(name = "backdrop_path")
    val backdropPath: String?,
    @Json(name = "created_by")
    val createdBys: List<CreatedBy>?,
    @Json(name = "episode_run_time")
    val episodeRunTime: List<Int>?,
    @Json(name = "first_air_date")
    val firstAirDate: String?,
    val genres: List<Genre>?,
    val homepage: String?,
    val id: Long,
    @Json(name = "in_production")
    val inProduction: Boolean,
    val languages: List<String>?,
    @Json(name = "last_air_date")
    val lastAirDate: String?,
    val name: String?,
    @Json(name = "number_of_episodes")
    val numberOfEpisodes: Int?,
    @Json(name = "number_of_seasons")
    val numberOfSeasons: Int?,
    @Json(name = "origin_country")
    val originCountry: List<String>?,
    @Json(name = "original_language")
    val originalLanguage: String?,
    @Json(name = "original_name")
    val originalName: String?,
    val overview: String?,
    val popularity: Double?,
    @Json(name = "poster_path")
    val posterPath: String?,
    val seasons: List<Season>?,
    @Json(name = "spoken_languages")
    val spokenLanguages: List<SpokenLanguage>?,
    val status: String?,
    val tagline: String?,
    val type: String?,
    @Json(name = "vote_average")
    val voteAverage: Double?,
    @Json(name = "vote_count")
    val voteCount: Int?
) {
    @JsonClass(generateAdapter = true)
    data class CreatedBy(
        val id: Int?,
        @Json(name = "credit_id")
        val creditId: String?,
        val name: String,
        val gender: Int?,
        @Json(name = "profile_path")
        val profilePath: String?
    )

    @JsonClass(generateAdapter = true)
    data class Season(
        @Json(name = "air_date")
        val airDate: String?,
        @Json(name = "episode_count")
        val episodeCount: Int?,
        val id: Int?,
        val name: String?,
        val overview: String?,
        @Json(name = "poster_path")
        val posterPath: String?,
        @Json(name = "season_number")
        val seasonNumber: Int?
    )

    @JsonClass(generateAdapter = true)
    data class SpokenLanguage(
        @Json(name = "english_name")
        val englishName: String,
        @Json(name = "iso_639_1")
        val iso6391: String,
        val name: String
    )
}

fun Tv.toElement(): Element {
    return Element(
        backdrop = backdropPath ?: "",
        id = "tv$id",
        language = originalLanguage?.uppercase() ?: "",
        movieDbId = id,
        mediaType = "tv",
        overview = overview ?: "",
        poster = posterPath ?: "",
        releaseDate = firstAirDate ?: "",
        title = name ?: ""
    )
}