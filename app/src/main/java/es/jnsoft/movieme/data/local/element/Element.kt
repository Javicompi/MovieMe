package es.jnsoft.movieme.data.local.element

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import es.jnsoft.movieme.data.network.model.movie.Movie
import es.jnsoft.movieme.data.network.model.tv.Tv
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class Element(
    val backdrop: String,
    @PrimaryKey
    val id: String,
    val language: String,
    val movieDbId: Long,
    val mediaType: String,
    val overview: String,
    val poster: String,
    val releaseDate: String,
    val title: String
) : Parcelable

fun Element.toMovie(): Movie {
    return Movie(
        backdropPath = backdrop,
        id = movieDbId,
        originalLanguage = language,
        overview = overview,
        posterPath = poster,
        releaseDate = releaseDate,
        title = title
    )
}

fun Element.toTv(): Tv {
    return Tv(
        backdropPath = backdrop,
        id = movieDbId,
        originalLanguage = language,
        overview = overview,
        posterPath = poster,
        firstAirDate = releaseDate,
        name = title
    )
}