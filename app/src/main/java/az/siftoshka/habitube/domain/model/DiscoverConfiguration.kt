package az.siftoshka.habitube.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DiscoverConfiguration(
    var isMovieSelected: Boolean? = true,
    var sortCategory: String? = null,
    var yearStart: String? = null,
    var yearEnd: String? = null,
    var ratingStart: String? = null,
    var ratingEnd: String? = null,
    var genres: String? = null,
    var networks: String? = null
) : Parcelable
