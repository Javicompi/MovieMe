package es.jnsoft.movieme.utils

import android.icu.text.SimpleDateFormat
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.google.android.material.floatingactionbutton.FloatingActionButton
import es.jnsoft.movieme.R
import es.jnsoft.movieme.data.network.model.common.Genre
import es.jnsoft.movieme.data.network.model.search.Search
import es.jnsoft.movieme.data.network.model.trend.Trend
import es.jnsoft.movieme.data.network.model.trend.TrendMediaType
import es.jnsoft.movieme.data.network.model.tv.Tv.CreatedBy

object BindingAdapters {

    @BindingAdapter("android:fadeVisible")
    @JvmStatic
    fun setFadeVisible(view: View, visible: Boolean? = true) {
        if (view.tag == null) {
            view.tag = true
            view.visibility = if (visible == true) View.VISIBLE else View.GONE
        } else {
            view.animate().cancel()
            if (visible == true) {
                if (view.visibility == View.GONE)
                    view.fadeIn()
            } else {
                if (view.visibility == View.VISIBLE)
                    view.fadeOut()
            }
        }
    }

    @BindingAdapter("android:fabIcon")
    @JvmStatic
    fun FloatingActionButton.setIcon(isSaved: Boolean? = false) {
        if (isSaved == true) {
            this.setImageDrawable(
                ResourcesCompat.getDrawable(
                    resources,
                    R.drawable.ic_favourite,
                    null
                )
            )
        } else {
            this.setImageDrawable(
                ResourcesCompat.getDrawable(
                    resources,
                    R.drawable.ic_favourite_empty,
                    null
                )
            )
        }
    }

    @BindingAdapter("android:trendTitle")
    @JvmStatic
    fun TextView.setTrendTitle(trend: Trend) {
        text = if (!trend.title.isNullOrEmpty()) {
            trend.title
        } else if (!trend.originalTitle.isNullOrEmpty()) {
            trend.originalTitle
        } else if (!trend.name.isNullOrEmpty()) {
            trend.name
        } else {
            trend.originalName
        }
    }

    @BindingAdapter("android:searchTitle")
    @JvmStatic
    fun TextView.setSearchTitle(search: Search) {
        text = if (!search.title.isNullOrEmpty()) {
            search.title
        } else if (!search.originalTitle.isNullOrEmpty()) {
            search.originalTitle
        } else if (!search.name.isNullOrEmpty()) {
            search.name
        } else {
            search.originalName
        }
    }

    @BindingAdapter("android:releaseYear")
    @JvmStatic
    fun TextView.setReleaseYear(search: Search) {
        val parser = SimpleDateFormat("yyyy-MM-dd")
        val formatter = SimpleDateFormat("yyyy")
        val toFormat = search.releaseDate ?: search.firstAirDate
        text = if (!toFormat.isNullOrEmpty()) {
            val year = formatter.format(parser.parse(toFormat))
            resources.getString(R.string.release, year)
        } else {
            resources.getString(R.string.release, "Unknown")
        }
    }

    @BindingAdapter("android:imageUrl")
    @JvmStatic
    fun ImageView.setImageUrl(url: String?) {
        url?.let {
            val fullUrl = Constants.IMAGE_BASE_URL + url
            Glide.with(this)
                .load(fullUrl)
                .error(R.drawable.poster_placeholder)
                .into(this)
        }
    }

    @BindingAdapter("android:mediaType")
    @JvmStatic
    fun ImageView.setMediaType(mediaType: String?) {
        mediaType?.let {
            if (it == TrendMediaType.MOVIE.value) {
                this.setImageDrawable(
                    ResourcesCompat.getDrawable(
                        resources,
                        R.drawable.ic_movie,
                        null
                    )
                )
            } else {
                this.setImageDrawable(
                    ResourcesCompat.getDrawable(
                        resources,
                        R.drawable.ic_tv,
                        null
                    )
                )
            }
        }
    }

    @BindingAdapter("android:genres")
    @JvmStatic
    fun TextView.setGenres(genres: List<Genre>?) {
        text = if (genres.isNullOrEmpty()) {
            ""
        } else {
            val listOfValues = genres.map {
                it.name.lowercase()
            }
            val result = listOfValues.joinToString(", ")
            result.replaceFirstChar { it.uppercase() }
        }
    }

    @BindingAdapter("android:creators")
    @JvmStatic
    fun TextView.setCreators(creators: List<CreatedBy>?) {
        text = if (creators.isNullOrEmpty()) {
            ""
        } else {
            creators.joinToString(", ") {
                it.name
            }
        }
    }
}