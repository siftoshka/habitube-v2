package az.siftoshka.habitube.presentation.screens.discover.sort

import androidx.annotation.StringRes
import az.siftoshka.habitube.R
import az.siftoshka.habitube.presentation.screens.discover.DiscoverScreen

/**
 * Item for [DiscoverScreen].
 */
class DiscoverSortItem(
    val category: DiscoverSortCategory,
    @StringRes var text: Int,
    var code: String
)

enum class DiscoverSortCategory {
    POPULARITY_DESC,
    POPULARITY_ASC,
    DATE_DESC,
    DATE_ASC,
    REVENUE_DESC,
    REVENUE_ASC,
    TITLE_DESC,
    TITLE_ASC,
    RATING_DESC,
    RATING_ASC
}

val types = mutableListOf(
    DiscoverSortItem(
        DiscoverSortCategory.POPULARITY_DESC,
        R.string.text_sort_popularity_desc,
        "popularity.desc"
    ),
    DiscoverSortItem(
        DiscoverSortCategory.DATE_DESC,
        R.string.text_sort_date_desc,
        "release_date.desc"
    ),
    DiscoverSortItem(
        DiscoverSortCategory.REVENUE_DESC,
        R.string.text_sort_revenue_desc,
        "revenue.desc"
    ),
    DiscoverSortItem(
        DiscoverSortCategory.TITLE_DESC,
        R.string.text_sort_name_desc,
        "original_title.desc"
    ),
    DiscoverSortItem(
        DiscoverSortCategory.RATING_DESC,
        R.string.text_sort_vote_desc,
        "vote_average.desc"
    ),
    DiscoverSortItem(
        DiscoverSortCategory.POPULARITY_ASC,
        R.string.text_sort_popularity_asc,
        "popularity.asc"
    ),
    DiscoverSortItem(
        DiscoverSortCategory.DATE_ASC,
        R.string.text_sort_date_asc,
        "release_date.asc"
    ),
    DiscoverSortItem(
        DiscoverSortCategory.REVENUE_ASC,
        R.string.text_sort_revenue_asc,
        "revenue.asc"
    ),
    DiscoverSortItem(
        DiscoverSortCategory.TITLE_ASC,
        R.string.text_sort_name_asc,
        "original_title.asc"
    ),
    DiscoverSortItem(
        DiscoverSortCategory.RATING_ASC,
        R.string.text_sort_vote_asc,
        "vote_average.asc"
    )
)

val showTypes = mutableListOf(
    DiscoverSortItem(
        DiscoverSortCategory.POPULARITY_DESC,
        R.string.text_sort_popularity_desc,
        "popularity.desc"
    ),
    DiscoverSortItem(
        DiscoverSortCategory.DATE_DESC,
        R.string.text_sort_date_desc,
        "first_air_date.desc"
    ),
    DiscoverSortItem(
        DiscoverSortCategory.RATING_DESC,
        R.string.text_sort_vote_desc,
        "vote_average.desc"
    ),
    DiscoverSortItem(
        DiscoverSortCategory.POPULARITY_ASC,
        R.string.text_sort_popularity_asc,
        "popularity.asc"
    ),
    DiscoverSortItem(
        DiscoverSortCategory.DATE_ASC,
        R.string.text_sort_date_asc,
        "first_air_date.asc"
    ),
    DiscoverSortItem(
        DiscoverSortCategory.RATING_ASC,
        R.string.text_sort_vote_asc,
        "vote_average.asc"
    )
)