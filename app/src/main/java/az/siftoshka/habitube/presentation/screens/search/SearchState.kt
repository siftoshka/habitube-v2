package az.siftoshka.habitube.presentation.screens.search

import az.siftoshka.habitube.domain.model.MediaLite

/**
 * The state data class for Search Screen.
 */
data class SearchState(
    val isLoading: Boolean = false,
    val media: List<MediaLite> = emptyList(),
    val error: String = ""
)