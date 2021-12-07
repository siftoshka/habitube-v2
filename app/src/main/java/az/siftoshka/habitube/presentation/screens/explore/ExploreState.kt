package az.siftoshka.habitube.presentation.screens.explore

import az.siftoshka.habitube.domain.model.MovieLite

/**
 * The state data class for Explore Screen.
 */
data class ExploreState(
    val isLoading: Boolean = false,
    val media: List<MovieLite> = emptyList(),
    val error: String = ""
)