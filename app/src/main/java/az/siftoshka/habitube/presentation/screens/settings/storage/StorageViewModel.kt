package az.siftoshka.habitube.presentation.screens.settings.storage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import az.siftoshka.habitube.domain.usecases.local.PlannedMoviesUseCase
import az.siftoshka.habitube.domain.usecases.local.PlannedTvShowUseCase
import az.siftoshka.habitube.domain.usecases.local.WatchedMoviesUseCase
import az.siftoshka.habitube.domain.usecases.local.WatchedTvShowUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * [ViewModel] of the Storage Screen.
 */
@HiltViewModel
class StorageViewModel @Inject constructor(
    private val watchedMoviesUseCase: WatchedMoviesUseCase,
    private val watchedTvShowUseCase: WatchedTvShowUseCase,
    private val plannedMoviesUseCase: PlannedMoviesUseCase,
    private val plannedTvShowUseCase: PlannedTvShowUseCase
) : ViewModel() {

    fun deleteWatchedMovies() {
        viewModelScope.launch {
            watchedMoviesUseCase.deleteAllMovies()
        }
    }

    fun deleteWatchedTvShows() {
        viewModelScope.launch {
            watchedTvShowUseCase.deleteAllShows()
        }
    }

    fun deletePlannedMovies() {
        viewModelScope.launch {
            plannedMoviesUseCase.deleteAllMovies()
        }
    }

    fun deletePlannedTvShows() {
        viewModelScope.launch {
            plannedTvShowUseCase.deleteAllShows()
        }
    }

    fun deleteAll() {
        viewModelScope.launch {
            watchedMoviesUseCase.deleteAllMovies()
            watchedTvShowUseCase.deleteAllShows()
            plannedMoviesUseCase.deleteAllMovies()
            plannedTvShowUseCase.deleteAllShows()
        }
    }
}