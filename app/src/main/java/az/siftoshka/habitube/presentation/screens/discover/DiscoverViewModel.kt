package az.siftoshka.habitube.presentation.screens.discover

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import az.siftoshka.habitube.presentation.screens.discover.sort.DiscoverSortCategory
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * [ViewModel] of the Discover Screens.
 */
@HiltViewModel
class DiscoverViewModel @Inject constructor(

) : ViewModel() {

    var isMovieSelected = mutableStateOf(true)
    var sortCategory = mutableStateOf(DiscoverSortCategory.POPULARITY_DESC)

}