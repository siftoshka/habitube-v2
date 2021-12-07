package az.siftoshka.habitube.presentation.screens.explore

import az.siftoshka.habitube.domain.model.MediaLite

/**
 * The state data class for Explore Screen.
 */
data class ExploreState(
    val isLoading: Boolean = false,
    val media: List<MediaLite> = emptyList(),
    val error: String = ""
)