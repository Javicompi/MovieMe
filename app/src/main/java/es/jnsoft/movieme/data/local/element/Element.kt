package es.jnsoft.movieme.data.local.element

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import es.jnsoft.movieme.data.network.model.movie.Movie
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class Element(
    val backdrop: String,
    @PrimaryKey
    val id: String,
    val movieDbId: Long,
    val mediaType: String,
    val overview: String,
    val poster: String,
    val title: String
) : Parcelable

fun Element.toMovie(): Movie {
    return Movie(
        backdropPath = backdrop,
        id = movieDbId,
        overview = overview,
        posterPath = poster,
        title = title
    )
}