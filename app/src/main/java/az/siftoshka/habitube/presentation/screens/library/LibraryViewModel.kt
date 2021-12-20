package az.siftoshka.habitube.presentation.screens.library

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import az.siftoshka.habitube.domain.model.Movie
import az.siftoshka.habitube.domain.model.TvShow
import az.siftoshka.habitube.domain.usecases.local.PlannedMoviesUseCase
import az.siftoshka.habitube.domain.usecases.local.PlannedTvShowUseCase
import az.siftoshka.habitube.domain.usecases.local.WatchedMoviesUseCase
import az.siftoshka.habitube.domain.usecases.local.WatchedTvShowUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

/**
 * [ViewModel] of the Library Screen.
 */
@HiltViewModel
class LibraryViewModel @Inject constructor(
    private val plannedMoviesUseCase: PlannedMoviesUseCase,
    private val plannedTvShowUseCase: PlannedTvShowUseCase,
    private val watchedMoviesUseCase: WatchedMoviesUseCase,
    private val watchedTvShowUseCase: WatchedTvShowUseCase,
) : ViewModel() {

    var watchedMovies = mutableStateOf<List<Movie>>(emptyList())
    var plannedMovies = mutableStateOf<List<Movie>>(emptyList())
    val watchedShows = mutableStateOf<List<TvShow>>(emptyList())
    val plannedShows = mutableStateOf<List<TvShow>>(emptyList())

    val isMoviesSelected = mutableStateOf(true)
    val isShowsSelected = mutableStateOf(false)

    init { updateConfiguration() }

    fun updateConfiguration() {
        if (isMoviesSelected.value) {
            clean()
            getWatchedMovies()
            getPlannedMovies()
        }
        else if (isShowsSelected.value) {
            clean()
            getWatchedTvShows()
            getPlannedTvShows()
        }
    }

    private fun getWatchedMovies() {
        watchedMoviesUseCase.getMovies().onEach { result ->
            watchedMovies.value = result
        }.launchIn(viewModelScope)
    }

    private fun getPlannedMovies() {
        plannedMoviesUseCase.getMovies().onEach { result ->
            plannedMovies.value = result
        }.launchIn(viewModelScope)
    }

    private fun getWatchedTvShows() {
        watchedTvShowUseCase.getShows().onEach { result ->
            watchedShows.value = result
        }.launchIn(viewModelScope)
    }

    private fun getPlannedTvShows() {
        plannedTvShowUseCase.getShows().onEach { result ->
            plannedShows.value = result
        }.launchIn(viewModelScope)
    }

    private fun clean() {
        watchedMovies.value = emptyList()
        plannedMovies.value = emptyList()
        watchedShows.value = emptyList()
        plannedShows.value = emptyList()
    }
}