package az.siftoshka.habitube.presentation.screens.show

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import az.siftoshka.habitube.domain.model.TvShow
import az.siftoshka.habitube.domain.usecases.local.PlannedTvShowUseCase
import az.siftoshka.habitube.domain.usecases.local.WatchedTvShowUseCase
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
import kotlinx.coroutines.launch
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
    private val plannedTvShowUseCase: PlannedTvShowUseCase,
    private val watchedTvShowUseCase: WatchedTvShowUseCase,
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

    var rating = mutableStateOf(0f)
    var isWatched = mutableStateOf(false)
    var isPlanned = mutableStateOf(false)

    var showId = 0
    var show: TvShow? = null

    init {
        savedStateHandle.get<String>(PARAM_TV_SHOW_ID)?.let {
            showId = it.toInt()
            getShow(showId)
            getVideos(showId)
            getCredits(showId)
            getSimilarShows(showId)
            isLocalExists(showId)
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
                    show = result.data
                }
                is Resource.Error -> {
                    _showState.value = ShowState(error = result.message ?: "Error")
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun isLocalExists(showId: Int) {
        viewModelScope.launch {
            watchedTvShowUseCase.getShow(showId).also {
                isWatched.value = it.id == showId
                rating.value = it.myRating ?: 0f
            }
            plannedTvShowUseCase.getShow(showId).also {
                isPlanned.value = it.id == showId
            }
        }
    }

    fun addWatched(rating: Float?) {
        viewModelScope.launch {
            show?.let { watchedTvShowUseCase.addShow(it, rating) }
        }
    }

    fun deleteWatched() {
        viewModelScope.launch {
            show?.let { watchedTvShowUseCase.deleteShow(it) }
            isWatched.value = false
            rating.value = 0f
        }
    }

    fun addPlanned() {
        viewModelScope.launch {
            show?.let { plannedTvShowUseCase.addShow(it) }
        }
    }

    fun deletePlanned() {
        viewModelScope.launch {
            show?.let { plannedTvShowUseCase.deleteShow(it) }
        }
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
                    result.data?.forEach { it.voteAverage = watchedTvShowUseCase.getShowRating(it.id ?: 0).toDouble() }
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
                            result.data?.forEach { it.voteAverage = watchedTvShowUseCase.getShowRating(it.id ?: 0).toDouble() }
                            _similarState.value = SimilarShowsState(
                                shows = _similarState.value.shows.plus(result.data.orEmpty())
                            )
                        }
                        else -> {
                            _similarState.value = SimilarShowsState(
                                shows = _similarState.value.shows.plus(emptyList())
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