package az.siftoshka.habitube.presentation.screens.settings.sort

import androidx.annotation.StringRes
import az.siftoshka.habitube.R

/**
 * Item for [SortScreen].
 */
class SortItem(
    val type: SortType,
    @StringRes var text: Int,
)

enum class SortType {
    RECENTLY,
    FIRST,
    TITLE,
    RATING,
    RELEASE_DESC,
    RELEASE_ASC
}

val list = mutableListOf(
    SortItem(
        SortType.RECENTLY,
        R.string.text_recently_added
    ),
    SortItem(
        SortType.FIRST,
        R.string.text_added_first
    ),
    SortItem(
        SortType.TITLE,
        R.string.text_sort_title
    ),
    SortItem(
        SortType.RATING,
        R.string.text_sort_rating
    ),
    SortItem(
        SortType.RELEASE_DESC,
        R.string.text_release_desc
    ),
    SortItem(
        SortType.RELEASE_ASC,
        R.string.text_release_asc
    )
)