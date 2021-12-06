package az.siftoshka.habitube.presentation.screens.explore

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import az.siftoshka.habitube.domain.model.MovieLite
import az.siftoshka.habitube.domain.usecases.GetExploreUseCase
import az.siftoshka.habitube.domain.util.ExploreType
import az.siftoshka.habitube.domain.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

/**
 * [ViewModel] of the ExploreScreen.
 */
@HiltViewModel
class ExploreViewModel @Inject constructor(
    private val getExploreUseCase: GetExploreUseCase
) : ViewModel() {

    private val _exploreUpcomingMoviesState = mutableStateOf(ExploreMoviesState())
    val exploreUpcomingMoviesState: State<ExploreMoviesState> = _exploreUpcomingMoviesState

    private val _exploreTrendingMoviesState = mutableStateOf(ExploreMoviesState())
    val exploreTrendingMoviesState: State<ExploreMoviesState> = _exploreTrendingMoviesState

    private val _exploreTrendingTvShowsState = mutableStateOf(ExploreMoviesState())
    val exploreTrendingTvShowsState: State<ExploreMoviesState> = _exploreTrendingTvShowsState

    private val _exploreAirTodayTvShowsState = mutableStateOf(ExploreMoviesState())
    val exploreAirTodayTvShowsState: State<ExploreMoviesState> = _exploreAirTodayTvShowsState

    init {
        getUpcomingMovies()
        getTrendingMovies()
        getTrendingTvShows()
        getAirTodayTvShows()
    }

    private fun getUpcomingMovies() {
        getExploreUseCase(page = 1, ExploreType.Upcoming).onEach { result ->
            when (result) {
                is Resource.Loading -> {
                    _exploreUpcomingMoviesState.value = ExploreMoviesState(isLoading = true)
                }
                is Resource.Success -> {
                    _exploreUpcomingMoviesState.value = ExploreMoviesState(movies = result.data ?: emptyList())
                }
                is Resource.Error -> {
                    _exploreUpcomingMoviesState.value = ExploreMoviesState(error = result.message ?: "Error")
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun getTrendingMovies() {
        getExploreUseCase(page = 1, ExploreType.TrendingMovies).onEach { result ->
            when (result) {
                is Resource.Loading -> {
                    _exploreTrendingMoviesState.value = ExploreMoviesState(isLoading = true)
                }
                is Resource.Success -> {
                    _exploreTrendingMoviesState.value = ExploreMoviesState(movies = result.data ?: emptyList())
                }
                is Resource.Error -> {
                    _exploreTrendingMoviesState.value = ExploreMoviesState(error = result.message ?: "Error")
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun getTrendingTvShows() {
        getExploreUseCase(page = 1, ExploreType.TrendingTvShows).onEach { result ->
            when (result) {
                is Resource.Loading -> {
                    _exploreTrendingTvShowsState.value = ExploreMoviesState(isLoading = true)
                }
                is Resource.Success -> {
                    _exploreTrendingTvShowsState.value = ExploreMoviesState(movies = result.data ?: emptyList())
                }
                is Resource.Error -> {
                    _exploreTrendingTvShowsState.value = ExploreMoviesState(error = result.message ?: "Error")
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun getAirTodayTvShows() {
        getExploreUseCase(page = 1, ExploreType.AirToday).onEach { result ->
            when (result) {
                is Resource.Loading -> {
                    _exploreAirTodayTvShowsState.value = ExploreMoviesState(isLoading = true)
                }
                is Resource.Success -> {
                    _exploreAirTodayTvShowsState.value = ExploreMoviesState(movies = result.data ?: emptyList())
                }
                is Resource.Error -> {
                    _exploreAirTodayTvShowsState.value = ExploreMoviesState(error = result.message ?: "Error")
                }
            }
        }.launchIn(viewModelScope)
    }
}

data class ExploreMoviesState(
    val isLoading: Boolean = false,
    val movies: List<MovieLite> = emptyList(),
    val error: String = ""
)