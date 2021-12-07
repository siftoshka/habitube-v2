package az.siftoshka.habitube.presentation.screens.movie

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import az.siftoshka.habitube.presentation.util.NavigationConstants.PARAM_MOVIE_ID
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * [ViewModel] of the ExploreScreen.
 */
@HiltViewModel
class MovieViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    var movieId: String = ""

    init {
        savedStateHandle.get<String>(PARAM_MOVIE_ID)?.let {
            movieId = it
        }
    }

}