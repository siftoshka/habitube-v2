package az.siftoshka.habitube.presentation.screens.home

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import az.siftoshka.habitube.domain.repository.LocalRepository
import az.siftoshka.habitube.domain.usecases.remote.GetExploreUseCase
import az.siftoshka.habitube.domain.util.Constants.PAGE_SIZE
import az.siftoshka.habitube.domain.util.ExploreType
import az.siftoshka.habitube.domain.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * [ViewModel] of the Home Screen.
 */
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getExploreUseCase: GetExploreUseCase,
    private val localRepository: LocalRepository
) : ViewModel() {

    private val _updateState = mutableStateOf(false)
    val updateState: MutableState<Boolean> = _updateState

    private val _exploreUpcomingMoviesState = mutableStateOf(ExploreState())
    val exploreUpcomingMoviesState: State<ExploreState> = _exploreUpcomingMoviesState

    private val _exploreTrendingMoviesState = mutableStateOf(ExploreState())
    val exploreTrendingMoviesState: State<ExploreState> = _exploreTrendingMoviesState
    val trendingMoviesPage = mutableStateOf(1)
    private var trendingMoviesPosition = 0

    private val _exploreTrendingTvShowsState = mutableStateOf(ExploreState())
    val exploreTrendingTvShowsState: State<ExploreState> = _exploreTrendingTvShowsState
    val trendingTvShowsPage = mutableStateOf(1)
    private var trendingTvShowsPosition = 0

    private val _exploreAirTodayTvShowsState = mutableStateOf(ExploreState())
    val exploreAirTodayTvShowsState: State<ExploreState> = _exploreAirTodayTvShowsState
    val airTodayTvShowsPage = mutableStateOf(1)
    private var airTodayTvShowsPosition = 0

    init {
        updateScreen()
    }

    fun updateScreen() {
        getUpcomingMovies()
        getTrendingMovies()
        getTrendingTvShows()
        getAirTodayTvShows()
    }

    fun isUpdateShown() {
        viewModelScope.launch {
            delay(1000L)
            _updateState.value = !localRepository.isUpdateShown()
        }
    }

    fun setUpdateShown() {
        localRepository.setVersionName().also {
            _updateState.value = !localRepository.isUpdateShown()
        }
    }

    private fun getUpcomingMovies() {
        getExploreUseCase(page = 1, ExploreType.Upcoming).onEach { result ->
            when (result) {
                is Resource.Loading -> {
                    _exploreUpcomingMoviesState.value = ExploreState(isLoading = true)
                }
                is Resource.Success -> {
                    _exploreUpcomingMoviesState.value = ExploreState(media = result.data ?: emptyList())
                }
                is Resource.Error -> {
                    _exploreUpcomingMoviesState.value = ExploreState(error = result.message ?: "Error")
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun getTrendingMovies() {
        getExploreUseCase(page = 1, ExploreType.TrendingMovies).onEach { result ->
            when (result) {
                is Resource.Loading -> {
                    _exploreTrendingMoviesState.value = ExploreState(isLoading = true)
                }
                is Resource.Success -> {
                    _exploreTrendingMoviesState.value = ExploreState(media = result.data ?: emptyList())
                }
                is Resource.Error -> {
                    _exploreTrendingMoviesState.value = ExploreState(error = result.message ?: "Error")
                }
            }
        }.launchIn(viewModelScope)
    }

    fun getMoreTrendingMovies() {
        if ((trendingMoviesPosition + 1) >= (trendingMoviesPage.value * PAGE_SIZE)) {
            trendingMoviesPage.value = trendingMoviesPage.value + 1
            if (trendingMoviesPage.value > 1) {
                getExploreUseCase(page = trendingMoviesPage.value, ExploreType.TrendingMovies).onEach { result ->
                    when (result) {
                        is Resource.Success -> {
                            _exploreTrendingMoviesState.value = ExploreState(
                                media = _exploreTrendingMoviesState.value.media.plus(result.data.orEmpty())
                            )
                        }
                        else -> {
                            _exploreTrendingMoviesState.value = ExploreState(
                                media = _exploreTrendingMoviesState.value.media.plus(emptyList())
                            )
                        }
                    }
                }.launchIn(viewModelScope)
            }
        }
    }

    fun onChangeTrendingMoviesPosition(position: Int) {
        trendingMoviesPosition = position
    }

    private fun getTrendingTvShows() {
        getExploreUseCase(page = 1, ExploreType.TrendingTvShows).onEach { result ->
            when (result) {
                is Resource.Loading -> {
                    _exploreTrendingTvShowsState.value = ExploreState(isLoading = true)
                }
                is Resource.Success -> {
                    _exploreTrendingTvShowsState.value = ExploreState(media = result.data ?: emptyList())
                }
                is Resource.Error -> {
                    _exploreTrendingTvShowsState.value = ExploreState(error = result.message ?: "Error")
                }
            }
        }.launchIn(viewModelScope)
    }

    fun getMoreTrendingTvShows() {
        if ((trendingTvShowsPosition + 1) >= (trendingTvShowsPage.value * PAGE_SIZE)) {
            trendingTvShowsPage.value = trendingTvShowsPage.value + 1
            if (trendingTvShowsPage.value > 1) {
                getExploreUseCase(page = trendingTvShowsPage.value, ExploreType.TrendingTvShows).onEach { result ->
                    when (result) {
                        is Resource.Success -> {
                            _exploreTrendingTvShowsState.value = ExploreState(
                                media = _exploreTrendingTvShowsState.value.media.plus(result.data.orEmpty())
                            )
                        }
                        else -> {
                            _exploreTrendingTvShowsState.value = ExploreState(
                                media = _exploreTrendingTvShowsState.value.media.plus(emptyList())
                            )
                        }
                    }
                }.launchIn(viewModelScope)
            }
        }
    }

    fun onChangeTrendingTvShowsPosition(position: Int) {
        trendingTvShowsPosition = position
    }

    private fun getAirTodayTvShows() {
        getExploreUseCase(page = 1, ExploreType.AirToday).onEach { result ->
            when (result) {
                is Resource.Loading -> {
                    _exploreAirTodayTvShowsState.value = ExploreState(isLoading = true)
                }
                is Resource.Success -> {
                    _exploreAirTodayTvShowsState.value = ExploreState(media = result.data ?: emptyList())
                }
                is Resource.Error -> {
                    _exploreAirTodayTvShowsState.value = ExploreState(error = result.message ?: "Error")
                }
            }
        }.launchIn(viewModelScope)
    }

    fun getMoreAirTodayTvShows() {
        if ((airTodayTvShowsPosition + 1) >= (airTodayTvShowsPage.value * PAGE_SIZE)) {
            airTodayTvShowsPage.value = airTodayTvShowsPage.value + 1
            if (airTodayTvShowsPage.value > 1) {
                getExploreUseCase(page = airTodayTvShowsPage.value, ExploreType.AirToday).onEach { result ->
                    when (result) {
                        is Resource.Success -> {
                            _exploreAirTodayTvShowsState.value = ExploreState(
                                media = _exploreAirTodayTvShowsState.value.media.plus(result.data.orEmpty())
                            )
                        }
                        else -> {
                            _exploreAirTodayTvShowsState.value = ExploreState(
                                media = _exploreAirTodayTvShowsState.value.media.plus(emptyList())
                            )
                        }
                    }
                }.launchIn(viewModelScope)
            }
        }
    }

    fun onChangeAirTodayTvShowsPosition(position: Int) {
        airTodayTvShowsPosition = position
    }
}