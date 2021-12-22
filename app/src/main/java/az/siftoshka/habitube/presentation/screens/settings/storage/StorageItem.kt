package az.siftoshka.habitube.presentation.screens.settings.storage

import androidx.annotation.StringRes
import az.siftoshka.habitube.R

/**
 * Item for [StorageScreen].
 */
class StorageItem(
    val type: StorageType,
    @StringRes var text: Int,
)

enum class StorageType {
    WATCHED_MOVIES,
    WATCHED_SHOWS,
    PLANNED_MOVIES,
    PLANNED_SHOWS,
    ALL
}

val list = mutableListOf(
    StorageItem(
        StorageType.WATCHED_MOVIES,
        R.string.text_delete_watched_movies
    ),
    StorageItem(
        StorageType.WATCHED_SHOWS,
        R.string.text_delete_watched_shows
    ),
    StorageItem(
        StorageType.PLANNED_MOVIES,
        R.string.text_delete_planned_movies
    ),
    StorageItem(
        StorageType.PLANNED_SHOWS,
        R.string.text_delete_planned_shows
    ),
    StorageItem(
        StorageType.ALL,
        R.string.text_delete_all
    )
)