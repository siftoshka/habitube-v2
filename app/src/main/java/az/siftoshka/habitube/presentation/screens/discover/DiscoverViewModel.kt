package az.siftoshka.habitube.presentation.screens.discover

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import az.siftoshka.habitube.presentation.screens.discover.items.DiscoverGenresCategory
import az.siftoshka.habitube.presentation.screens.discover.items.DiscoverSortCategory
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * [ViewModel] of the Discover Screens.
 */
@HiltViewModel
class DiscoverViewModel @Inject constructor() : ViewModel() {

    var isMovieSelected = mutableStateOf(true)
    var sortCategory = mutableStateOf(DiscoverSortCategory.POPULARITY_DESC)
    var yearRange = mutableStateOf(1900f..2021f)
    var ratingRange = mutableStateOf(4f..10f)
    var genres = mutableStateOf<List<DiscoverGenresCategory>>(emptyList())

    fun clear() {
        sortCategory.value = DiscoverSortCategory.POPULARITY_DESC
        genres.value = emptyList()
    }
}