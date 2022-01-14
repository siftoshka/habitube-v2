package az.siftoshka.habitube.presentation.screens.discover

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import az.siftoshka.habitube.domain.model.DiscoverConfiguration
import az.siftoshka.habitube.domain.util.toFormattedString
import az.siftoshka.habitube.presentation.screens.discover.items.DiscoverGenresCategory
import az.siftoshka.habitube.presentation.screens.discover.items.DiscoverNetworkCategory
import az.siftoshka.habitube.presentation.screens.discover.items.DiscoverSortCategory
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * [ViewModel] of the Discover Screen.
 */
@HiltViewModel
class DiscoverViewModel @Inject constructor() : ViewModel() {

    var isMovieSelected = mutableStateOf(true)
    var sortCategory = mutableStateOf(DiscoverSortCategory.POPULARITY_DESC)
    var yearRange = mutableStateOf(1900f..2021f)
    var ratingRange = mutableStateOf(4f..10f)
    var genres = mutableStateOf<List<DiscoverGenresCategory>>(emptyList())
    var networks = mutableStateOf<List<DiscoverNetworkCategory>>(emptyList())
    var genreList = emptyList<String>()
    var networkList = emptyList<String>()

    fun updateData(): DiscoverConfiguration {
        genres.value.forEach { genreList = genreList.plus(it.id) }
        networks.value.forEach { networkList = networkList.plus(it.id) }
        return DiscoverConfiguration(
            isMovieSelected = isMovieSelected.value,
            sortCategory = sortCategory.value.code,
            yearStart = yearRange.value.start.toInt().toString(),
            yearEnd = yearRange.value.endInclusive.toInt().toString(),
            ratingStart = ratingRange.value.start.toInt().toString(),
            ratingEnd = ratingRange.value.endInclusive.toInt().toString(),
            genres = genreList.toFormattedString(),
            networks = networkList.toFormattedString()
        ).also {
            genreList = emptyList()
            networkList = emptyList()
        }
    }

    fun clear() {
        sortCategory.value = DiscoverSortCategory.POPULARITY_DESC
        genres.value = emptyList()
        networks.value = emptyList()
    }
}