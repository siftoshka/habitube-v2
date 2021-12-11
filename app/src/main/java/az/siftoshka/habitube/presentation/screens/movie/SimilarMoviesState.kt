package az.siftoshka.habitube.presentation.screens.movie

import az.siftoshka.habitube.domain.model.MediaLite

/**
 * The state data class for Movie Screen.
 */
data class SimilarMoviesState(
    val isLoading: Boolean = false,
    val movies: List<MediaLite> = emptyList(),
    val error: String = ""
)