package az.siftoshka.habitube.presentation.screens.movie

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import az.siftoshka.habitube.domain.usecases.GetMovieUseCase
import az.siftoshka.habitube.domain.util.Resource
import az.siftoshka.habitube.presentation.util.NavigationConstants.PARAM_MOVIE_ID
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

/**
 * [ViewModel] of the Movie Screen.
 */
@HiltViewModel
class MovieViewModel @Inject constructor(
    private val getMovieUseCase: GetMovieUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _movieState = mutableStateOf(MovieState())
    val movieState: State<MovieState> = _movieState

    init {
        savedStateHandle.get<String>(PARAM_MOVIE_ID)?.let {
            getMovie(it.toInt())
        }
    }

    private fun getMovie(movieId: Int) {
        getMovieUseCase(movieId).onEach { result ->
            when (result) {
                is Resource.Loading -> {
                    _movieState.value = MovieState(isLoading = true)
                }
                is Resource.Success -> {
                    _movieState.value = MovieState(movie = result.data)
                }
                is Resource.Error -> {
                    _movieState.value = MovieState(error = result.message ?: "Error")
                }
            }
        }.launchIn(viewModelScope)
    }
}