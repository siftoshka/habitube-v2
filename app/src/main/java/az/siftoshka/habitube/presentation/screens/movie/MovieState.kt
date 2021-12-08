package az.siftoshka.habitube.presentation.screens.movie

import az.siftoshka.habitube.domain.model.Movie

/**
 * The state data class for Movie Screen.
 */
data class MovieState(
    val isLoading: Boolean = false,
    val movie: Movie? = null,
    val error: String = ""
)