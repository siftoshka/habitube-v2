package az.siftoshka.habitube.presentation.screens.movie

import az.siftoshka.habitube.domain.model.Omdb

/**
 * The state data class for Movie Screen.
 */
data class OmdbDataState(
    val isLoading: Boolean = false,
    val data: Omdb? = null,
    val error: String = ""
)