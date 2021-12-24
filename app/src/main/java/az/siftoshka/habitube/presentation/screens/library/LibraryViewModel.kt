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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.roundToInt

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

    val isLoading = mutableStateOf(true)
    val isMoviesSelected = mutableStateOf(true)
    val isShowsSelected = mutableStateOf(false)
    val isWatched = mutableStateOf(false)
    val mediaId = mutableStateOf(0)

    var movieState = mutableStateOf(Movie())
    var showState = mutableStateOf(TvShow())

    fun updateConfiguration() {
        if (isMoviesSelected.value) {
            clean()
            isLoading.value = true
            getWatchedMovies()
            getPlannedMovies()
        } else if (isShowsSelected.value) {
            clean()
            getWatchedTvShows()
            getPlannedTvShows()
        }
    }

    fun getWatchedMovie() {
        viewModelScope.launch {
            watchedMoviesUseCase.getMovie(mediaId.value).also { movieState.value = it }
        }
    }

    fun getWatchedTvShow() {
        viewModelScope.launch {
            watchedTvShowUseCase.getShow(mediaId.value).also { showState.value = it }
        }
    }

    fun getPlannedMovie() {
        viewModelScope.launch {
            plannedMoviesUseCase.getMovie(mediaId.value).also { movieState.value = it }
        }
    }

    fun getPlannedTVShow() {
        viewModelScope.launch {
            plannedTvShowUseCase.getShow(mediaId.value).also { showState.value = it }
        }
    }

    private fun getWatchedMovies() {
        watchedMoviesUseCase.getMovies()
            .debounce {
                if (it.isEmpty()) 0L
                else 400L
            }
            .flowOn(Dispatchers.IO)
            .onEach { result ->
                watchedMovies.value = result
                isLoading.value = false
            }
            .launchIn(viewModelScope)
    }

    private fun getPlannedMovies() {
        plannedMoviesUseCase.getMovies()
            .flowOn(Dispatchers.IO)
            .onEach { result -> plannedMovies.value = result }
            .launchIn(viewModelScope)
    }

    private fun getWatchedTvShows() {
        watchedTvShowUseCase.getShows()
            .flowOn(Dispatchers.IO)
            .onEach { result -> watchedShows.value = result }
            .launchIn(viewModelScope)
    }

    private fun getPlannedTvShows() {
        plannedTvShowUseCase.getShows()
            .flowOn(Dispatchers.IO)
            .onEach { result -> plannedShows.value = result }
            .launchIn(viewModelScope)
    }

    private fun clean() {
        watchedMovies.value = emptyList()
        plannedMovies.value = emptyList()
        watchedShows.value = emptyList()
        plannedShows.value = emptyList()
    }

    fun deleteMovie(movie: Movie, isWatched: Boolean) {
        viewModelScope.launch {
            if (isWatched) watchedMoviesUseCase.deleteMovie(movie)
            else plannedMoviesUseCase.deleteMovie(movie)
        }
    }

    fun deleteShow(show: TvShow, isWatched: Boolean) {
        viewModelScope.launch {
            if (isWatched) watchedTvShowUseCase.deleteShow(show)
            else plannedTvShowUseCase.deleteShow(show)
        }
    }

    fun switchToWatchedMovie(movie: Movie) {
        viewModelScope.launch {
            plannedMoviesUseCase.deleteMovie(movie)
            watchedMoviesUseCase.addMovie(movie, rating = movie.voteAverage?.roundToInt()?.toFloat())
        }
    }

    fun switchToWatchedShow(show: TvShow) {
        viewModelScope.launch {
            plannedTvShowUseCase.deleteShow(show)
            watchedTvShowUseCase.addShow(show, rating = show.voteAverage?.roundToInt()?.toFloat())
        }
    }
}