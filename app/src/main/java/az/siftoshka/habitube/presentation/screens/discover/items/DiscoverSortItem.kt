package az.siftoshka.habitube.presentation.screens.discover.items

import androidx.annotation.StringRes
import az.siftoshka.habitube.R
import az.siftoshka.habitube.presentation.screens.discover.DiscoverScreen

/**
 * Item for [DiscoverScreen].
 */
class DiscoverSortItem(
    val category: DiscoverSortCategory,
    @StringRes var text: Int
)

enum class DiscoverSortCategory(val code: String) {
    POPULARITY_DESC("popularity.desc"),
    POPULARITY_ASC("popularity.asc"),
    DATE_DESC("release_date.desc"),
    DATE_ASC("release_date.asc"),
    REVENUE_DESC("revenue.desc"),
    REVENUE_ASC("revenue.asc"),
    TITLE_DESC("original_title.desc"),
    TITLE_ASC("original_title.asc"),
    RATING_DESC("vote_average.desc"),
    RATING_ASC("vote_average.asc")
}

val types = mutableListOf(
    DiscoverSortItem(
        DiscoverSortCategory.POPULARITY_DESC,
        R.string.text_sort_popularity_desc
    ),
    DiscoverSortItem(
        DiscoverSortCategory.DATE_DESC,
        R.string.text_sort_date_desc
    ),
    DiscoverSortItem(
        DiscoverSortCategory.REVENUE_DESC,
        R.string.text_sort_revenue_desc
    ),
    DiscoverSortItem(
        DiscoverSortCategory.TITLE_DESC,
        R.string.text_sort_name_desc
    ),
    DiscoverSortItem(
        DiscoverSortCategory.RATING_DESC,
        R.string.text_sort_vote_desc
    ),
    DiscoverSortItem(
        DiscoverSortCategory.POPULARITY_ASC,
        R.string.text_sort_popularity_asc
    ),
    DiscoverSortItem(
        DiscoverSortCategory.DATE_ASC,
        R.string.text_sort_date_asc
    ),
    DiscoverSortItem(
        DiscoverSortCategory.REVENUE_ASC,
        R.string.text_sort_revenue_asc
    ),
    DiscoverSortItem(
        DiscoverSortCategory.TITLE_ASC,
        R.string.text_sort_name_asc
    ),
    DiscoverSortItem(
        DiscoverSortCategory.RATING_ASC,
        R.string.text_sort_vote_asc
    )
)

val showTypes = mutableListOf(
    DiscoverSortItem(
        DiscoverSortCategory.POPULARITY_DESC,
        R.string.text_sort_popularity_desc
    ),
    DiscoverSortItem(
        DiscoverSortCategory.DATE_DESC,
        R.string.text_sort_date_desc
    ),
    DiscoverSortItem(
        DiscoverSortCategory.RATING_DESC,
        R.string.text_sort_vote_desc
    ),
    DiscoverSortItem(
        DiscoverSortCategory.POPULARITY_ASC,
        R.string.text_sort_popularity_asc
    ),
    DiscoverSortItem(
        DiscoverSortCategory.DATE_ASC,
        R.string.text_sort_date_asc
    ),
    DiscoverSortItem(
        DiscoverSortCategory.RATING_ASC,
        R.string.text_sort_vote_asc
    )
)