package az.siftoshka.habitube.presentation.screens.discover.list

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import az.siftoshka.habitube.domain.model.DiscoverConfiguration
import az.siftoshka.habitube.domain.usecases.remote.GetDiscoverUseCase
import az.siftoshka.habitube.domain.util.Constants
import az.siftoshka.habitube.domain.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

/**
 * [ViewModel] of the Discover List Screen.
 */
@HiltViewModel
class DiscoverListViewModel @Inject constructor(
    private val discoverUseCase: GetDiscoverUseCase
) : ViewModel() {

    private val _discoverState = mutableStateOf(DiscoverState())
    val discoverState: State<DiscoverState> = _discoverState
    val discoverListPage = mutableStateOf(1)
    private var discoverListPosition = 0

    fun getDiscoverList(configuration: DiscoverConfiguration?) {
        discoverUseCase(
            page = 1,
            sort = configuration?.sortCategory.orEmpty(),
            genres = configuration?.genres.orEmpty(),
            networks = configuration?.networks.orEmpty(),
            isMovieSelected = configuration?.isMovieSelected ?: true,
            ratingGte = configuration?.ratingStart.orEmpty(),
            ratingLte = configuration?.ratingEnd.orEmpty(),
            yearGte = configuration?.yearStart.orEmpty() + "-01-01",
            yearLte = configuration?.yearEnd.orEmpty() + "-01-01"
        ).onEach { result ->
            when (result) {
                is Resource.Loading -> {
                    _discoverState.value = DiscoverState(isLoading = true)
                }
                is Resource.Success -> {
                    _discoverState.value = DiscoverState(media = result.data ?: emptyList())
                }
                is Resource.Error -> {
                    _discoverState.value = DiscoverState(error = result.message ?: "Error")
                }
            }
        }.launchIn(viewModelScope)
    }

    fun getMoreDiscoverList(configuration: DiscoverConfiguration?) {
        if ((discoverListPosition + 1) >= (discoverListPage.value * Constants.PAGE_SIZE)) {
            discoverListPage.value = discoverListPage.value + 1
            if (discoverListPage.value > 1) {
                discoverUseCase(
                    page = discoverListPage.value,
                    sort = configuration?.sortCategory.orEmpty(),
                    genres = configuration?.genres.orEmpty(),
                    networks = configuration?.networks.orEmpty(),
                    isMovieSelected = configuration?.isMovieSelected ?: true,
                    ratingGte = configuration?.ratingStart.orEmpty(),
                    ratingLte = configuration?.ratingEnd.orEmpty(),
                    yearGte = configuration?.yearStart.orEmpty() + "-01-01",
                    yearLte = configuration?.yearEnd.orEmpty() + "-01-01"
                ).onEach { result ->
                    when (result) {
                        is Resource.Success -> {
                            _discoverState.value = DiscoverState(media = _discoverState.value.media.plus(result.data.orEmpty()))
                        }
                        else -> {
                            _discoverState.value = DiscoverState(media = _discoverState.value.media.plus(emptyList()))
                        }
                    }
                }.launchIn(viewModelScope)
            }
        }
    }

    fun onChangeDiscoverListPosition(position: Int) {
        discoverListPosition = position
    }
}