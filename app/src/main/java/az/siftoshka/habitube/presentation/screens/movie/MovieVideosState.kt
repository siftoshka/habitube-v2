package az.siftoshka.habitube.presentation.screens.movie

import az.siftoshka.habitube.domain.model.Video

/**
 * The state data class for Movie Screen.
 */
data class MovieVideosState(
    val isLoading: Boolean = false,
    val videos: List<Video> = emptyList(),
    val error: String = ""
)