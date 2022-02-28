package az.siftoshka.habitube.presentation.screens.person

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import az.siftoshka.habitube.domain.usecases.local.WatchedMoviesUseCase
import az.siftoshka.habitube.domain.usecases.local.WatchedTvShowUseCase
import az.siftoshka.habitube.domain.usecases.remote.GetPersonCreditsUseCase
import az.siftoshka.habitube.domain.usecases.remote.GetPersonUseCase
import az.siftoshka.habitube.domain.util.MediaType
import az.siftoshka.habitube.domain.util.Resource
import az.siftoshka.habitube.presentation.util.NavigationConstants.PARAM_PERSON_ID
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

/**
 * [ViewModel] of the Person Screen.
 */
@HiltViewModel
class PersonViewModel @Inject constructor(
    private val getPersonUseCase: GetPersonUseCase,
    private val getPersonCreditsUseCase: GetPersonCreditsUseCase,
    private val watchedMoviesUseCase: WatchedMoviesUseCase,
    private val watchedTvShowUseCase: WatchedTvShowUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _personState = mutableStateOf(PersonState())
    val personState: State<PersonState> = _personState

    private val _personMoviesState = mutableStateOf(PersonCreditsState())
    val personMoviesState: State<PersonCreditsState> = _personMoviesState

    private val _personShowsState = mutableStateOf(PersonCreditsState())
    val personShowsState: State<PersonCreditsState> = _personShowsState

    init {
        savedStateHandle.get<String>(PARAM_PERSON_ID)?.let {
            getPerson(it.toInt())
            getMovieCredits(it.toInt())
            getShowCredits(it.toInt())
        }
    }

    private fun getPerson(personId: Int) {
        getPersonUseCase(personId).onEach { result ->
            when (result) {
                is Resource.Loading -> {
                    _personState.value = PersonState(isLoading = true)
                }
                is Resource.Success -> {
                    _personState.value = PersonState(person = result.data)
                }
                is Resource.Error -> {
                    _personState.value = PersonState(error = result.message ?: "Error")
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun getMovieCredits(personId: Int) {
        getPersonCreditsUseCase(personId, MediaType.Movie).onEach { result ->
            when (result) {
                is Resource.Loading -> {
                    _personMoviesState.value = PersonCreditsState(isLoading = true)
                }
                is Resource.Success -> {
                    result.data?.cast?.forEach { it.voteAverage = watchedMoviesUseCase.getMovieRating(it.id ?: 0).toDouble() }
                    result.data?.crew?.forEach { it.voteAverage = watchedMoviesUseCase.getMovieRating(it.id ?: 0).toDouble() }
                    _personMoviesState.value = PersonCreditsState(credits = result.data)
                }
                is Resource.Error -> {
                    _personMoviesState.value = PersonCreditsState(error = result.message ?: "Error")
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun getShowCredits(personId: Int) {
        getPersonCreditsUseCase(personId, MediaType.TvShow).onEach { result ->
            when (result) {
                is Resource.Loading -> {
                    _personShowsState.value = PersonCreditsState(isLoading = true)
                }
                is Resource.Success -> {
                    result.data?.cast?.forEach { it.voteAverage = watchedTvShowUseCase.getShowRating(it.id ?: 0).toDouble() }
                    result.data?.crew?.forEach { it.voteAverage = watchedTvShowUseCase.getShowRating(it.id ?: 0).toDouble() }
                    _personShowsState.value = PersonCreditsState(credits = result.data)
                }
                is Resource.Error -> {
                    _personShowsState.value = PersonCreditsState(error = result.message ?: "Error")
                }
            }
        }.launchIn(viewModelScope)
    }
}