package az.siftoshka.habitube.presentation.screens.show

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import az.siftoshka.habitube.domain.usecases.remote.GetCreditsUseCase
import az.siftoshka.habitube.domain.usecases.remote.GetSimilarUseCase
import az.siftoshka.habitube.domain.usecases.remote.GetTvShowUseCase
import az.siftoshka.habitube.domain.usecases.remote.GetVideosUseCase
import az.siftoshka.habitube.domain.util.Constants
import az.siftoshka.habitube.domain.util.MediaType
import az.siftoshka.habitube.domain.util.Resource
import az.siftoshka.habitube.presentation.util.NavigationConstants.PARAM_TV_SHOW_ID
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

/**
 * [ViewModel] of the Show Screen.
 */
@HiltViewModel
class ShowViewModel @Inject constructor(
    private val getTvShowUseCase: GetTvShowUseCase,
    private val getVideosUseCase: GetVideosUseCase,
    private val getCreditsUseCase: GetCreditsUseCase,
    private val getSimilarUseCase: GetSimilarUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _showState = mutableStateOf(ShowState())
    val showState: State<ShowState> = _showState

    private val _videosState = mutableStateOf(ShowVideosState())
    val videosState: State<ShowVideosState> = _videosState

    private val _creditsState = mutableStateOf(ShowCreditsState())
    val creditsState: State<ShowCreditsState> = _creditsState

    private val _similarState = mutableStateOf(SimilarShowsState())
    val similarState: State<SimilarShowsState> = _similarState
    val similarShowsPage = mutableStateOf(1)
    private var similarShowsPosition = 0

    var showId = 0

    init {
        savedStateHandle.get<String>(PARAM_TV_SHOW_ID)?.let {
            showId = it.toInt()
            getShow(it.toInt())
            getVideos(it.toInt())
            getCredits(it.toInt())
            getSimilarShows(it.toInt())
        }
    }

    private fun getShow(showId: Int) {
        getTvShowUseCase(showId).onEach { result ->
            when (result) {
                is Resource.Loading -> {
                    _showState.value = ShowState(isLoading = true)
                }
                is Resource.Success -> {
                    _showState.value = ShowState(show = result.data)
                }
                is Resource.Error -> {
                    _showState.value = ShowState(error = result.message ?: "Error")
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun getVideos(showId: Int) {
        getVideosUseCase(showId, MediaType.TvShow).onEach { result ->
            when (result) {
                is Resource.Loading -> {
                    _videosState.value = ShowVideosState(isLoading = true)
                }
                is Resource.Success -> {
                    _videosState.value = ShowVideosState(videos = result.data ?: emptyList())
                }
                is Resource.Error -> {
                    _videosState.value = ShowVideosState(error = result.message ?: "Error")
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun getCredits(showId: Int) {
        getCreditsUseCase(showId, MediaType.TvShow).onEach { result ->
            when (result) {
                is Resource.Loading -> {
                    _creditsState.value = ShowCreditsState(isLoading = true)
                }
                is Resource.Success -> {
                    _creditsState.value = ShowCreditsState(credits = result.data)
                }
                is Resource.Error -> {
                    _creditsState.value = ShowCreditsState(error = result.message ?: "Error")
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun getSimilarShows(showId: Int) {
        getSimilarUseCase(showId, page = 1, MediaType.TvShow).onEach { result ->
            when (result) {
                is Resource.Loading -> {
                    _similarState.value = SimilarShowsState(isLoading = true)
                }
                is Resource.Success -> {
                    _similarState.value = SimilarShowsState(shows = result.data ?: emptyList())
                }
                is Resource.Error -> {
                    _similarState.value = SimilarShowsState(error = result.message ?: "Error")
                }
            }
        }.launchIn(viewModelScope)
    }

    fun getMoreSimilarShows() {
        if ((similarShowsPosition + 1) >= (similarShowsPage.value * Constants.PAGE_SIZE)) {
            similarShowsPage.value = similarShowsPage.value + 1
            if (similarShowsPage.value > 1) {
                getSimilarUseCase(showId, similarShowsPage.value, MediaType.TvShow).onEach { result ->
                    when (result) {
                        is Resource.Success -> {
                            _similarState.value = SimilarShowsState(
                                shows = _similarState.value.shows.plus(result.data.orEmpty())
                            )
                        }
                    }
                }.launchIn(viewModelScope)
            }
        }
    }

    fun onChangePosition(position: Int) {
        similarShowsPosition = position
    }
}