package az.siftoshka.habitube.presentation.screens.search

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import az.siftoshka.habitube.domain.usecases.remote.GetSearchUseCase
import az.siftoshka.habitube.domain.util.Constants
import az.siftoshka.habitube.domain.util.Resource
import az.siftoshka.habitube.domain.util.SearchType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

/**
 * [ViewModel] of the Search Screen.
 */
@HiltViewModel
class SearchViewModel @Inject constructor(
    private val getSearchUseCase: GetSearchUseCase
) : ViewModel() {

    private val _searchState = mutableStateOf(SearchState())
    val searchState: State<SearchState> = _searchState
    val searchPage = mutableStateOf(1)
    private var searchPosition = 0

    var searchQuery = mutableStateOf("")
    val mediaType = mutableStateOf(SearchType.Multi)

    fun onQueryChanged(query: String) {
        searchQuery.value = query
        if (query.isNotEmpty()) search(query)
        else clean()
    }

    private fun clean() {
        _searchState.value = SearchState(media = emptyList())
        searchPosition = 0
        searchPage.value = 1
    }

    private fun search(query: String) {
        getSearchUseCase(query, page = 1, type = mediaType.value).onEach { result ->
            when (result) {
                is Resource.Loading -> {
                    _searchState.value = SearchState(isLoading = true)
                }
                is Resource.Success -> {
                    _searchState.value = SearchState(media = result.data ?: emptyList())
                }
                is Resource.Error -> {
                    _searchState.value = SearchState(error = result.message ?: "Error")
                }
            }
        }.launchIn(viewModelScope)
    }

    fun getMoreSearchResults() {
        if ((searchPosition + 1) >= (searchPage.value * Constants.PAGE_SIZE)) {
            searchPage.value = searchPage.value + 1
            if (searchPage.value > 1) {
                getSearchUseCase(page = searchPage.value, searchQuery = searchQuery.value, type = mediaType.value).onEach { result ->
                    when (result) {
                        is Resource.Success -> {
                            _searchState.value = SearchState(
                                media = _searchState.value.media.plus(result.data.orEmpty())
                            )
                        }
                        else -> {
                            _searchState.value = SearchState(
                                media = _searchState.value.media.plus(emptyList())
                            )
                        }
                    }
                }.launchIn(viewModelScope)
            }
        }
    }

    fun onChangeSearchPosition(position: Int) {
        searchPosition = position
    }

    fun changeSearchType(searchType: SearchType) {
        mediaType.value = searchType
        clean()
        search(searchQuery.value)
    }
}