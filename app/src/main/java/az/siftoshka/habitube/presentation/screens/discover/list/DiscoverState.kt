package az.siftoshka.habitube.presentation.screens.discover.list

import az.siftoshka.habitube.domain.model.MediaLite

/**
 * The state data class for Discover Screen.
 */
data class DiscoverState(
    val isLoading: Boolean = false,
    val media: List<MediaLite> = emptyList(),
    val error: String = ""
)