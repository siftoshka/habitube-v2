package az.siftoshka.habitube.presentation.screens.movie

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.*
import az.siftoshka.habitube.domain.usecases.GetMovieUseCase
import az.siftoshka.habitube.domain.usecases.GetVideosUseCase
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
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _imageOffset = MutableLiveData(240f)
    val imageOffset: LiveData<Float>
        get() = _imageOffset

    private val _movieState = mutableStateOf(MovieState())
    val movieState: State<MovieState> = _movieState

    private val _videosState = mutableStateOf(MovieVideosState())
    val videosState: State<MovieVideosState> = _videosState

    init {
        savedStateHandle.get<String>(PARAM_MOVIE_ID)?.let {
            getMovie(it.toInt())
            getVideos(it.toInt())
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
        getVideosUseCase(movieId).onEach { result ->
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

    fun updateOffset(state: Int) {
        println(state)
        _imageOffset.value = (200 - (state * 0.7)).toFloat()
    }
}