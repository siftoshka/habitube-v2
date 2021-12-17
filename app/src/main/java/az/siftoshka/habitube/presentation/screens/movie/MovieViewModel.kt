package az.siftoshka.habitube.presentation.screens.movie

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import az.siftoshka.habitube.domain.usecases.remote.GetCreditsUseCase
import az.siftoshka.habitube.domain.usecases.remote.GetMovieUseCase
import az.siftoshka.habitube.domain.usecases.remote.GetSimilarUseCase
import az.siftoshka.habitube.domain.usecases.remote.GetVideosUseCase
import az.siftoshka.habitube.domain.util.Constants
import az.siftoshka.habitube.domain.util.MediaType
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
    private val getVideosUseCase: GetVideosUseCase,
    private val getCreditsUseCase: GetCreditsUseCase,
    private val getSimilarUseCase: GetSimilarUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _movieState = mutableStateOf(MovieState())
    val movieState: State<MovieState> = _movieState

    private val _videosState = mutableStateOf(MovieVideosState())
    val videosState: State<MovieVideosState> = _videosState

    private val _creditsState = mutableStateOf(MovieCreditsState())
    val creditsState: State<MovieCreditsState> = _creditsState

    private val _similarState = mutableStateOf(SimilarMoviesState())
    val similarState: State<SimilarMoviesState> = _similarState
    val similarMoviesPage = mutableStateOf(1)
    private var similarMoviesPosition = 0

    var movieId = 0

    init {
        savedStateHandle.get<String>(PARAM_MOVIE_ID)?.let {
            movieId = it.toInt()
            getMovie(it.toInt())
            getVideos(it.toInt())
            getCredits(it.toInt())
            getSimilarMovies(it.toInt())
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

    private fun getVideos(movieId: Int) {
        getVideosUseCase(movieId, MediaType.Movie).onEach { result ->
            when (result) {
                is Resource.Loading -> {
                    _videosState.value = MovieVideosState(isLoading = true)
                }
                is Resource.Success -> {
                    _videosState.value = MovieVideosState(videos = result.data ?: emptyList())
                }
                is Resource.Error -> {
                    _videosState.value = MovieVideosState(error = result.message ?: "Error")
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun getCredits(movieId: Int) {
        getCreditsUseCase(movieId, MediaType.Movie).onEach { result ->
            when (result) {
                is Resource.Loading -> {
                    _creditsState.value = MovieCreditsState(isLoading = true)
                }
                is Resource.Success -> {
                    _creditsState.value = MovieCreditsState(credits = result.data)
                }
                is Resource.Error -> {
                    _creditsState.value = MovieCreditsState(error = result.message ?: "Error")
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun getSimilarMovies(movieId: Int) {
        getSimilarUseCase(movieId, page = 1, MediaType.Movie).onEach { result ->
            when (result) {
                is Resource.Loading -> {
                    _similarState.value = SimilarMoviesState(isLoading = true)
                }
                is Resource.Success -> {
                    _similarState.value = SimilarMoviesState(movies = result.data ?: emptyList())
                }
                is Resource.Error -> {
                    _similarState.value = SimilarMoviesState(error = result.message ?: "Error")
                }
            }
        }.launchIn(viewModelScope)
    }

    fun getMoreSimilarMovies() {
        if ((similarMoviesPosition + 1) >= (similarMoviesPage.value * Constants.PAGE_SIZE)) {
            similarMoviesPage.value = similarMoviesPage.value + 1
            if (similarMoviesPage.value > 1) {
                getSimilarUseCase(movieId, similarMoviesPage.value, MediaType.Movie).onEach { result ->
                    when (result) {
                        is Resource.Success -> {
                            _similarState.value = SimilarMoviesState(
                                movies = _similarState.value.movies.plus(result.data.orEmpty())
                            )
                        }
                    }
                }.launchIn(viewModelScope)
            }
        }
    }

    fun onChangePosition(position: Int) {
        similarMoviesPosition = position
    }
}