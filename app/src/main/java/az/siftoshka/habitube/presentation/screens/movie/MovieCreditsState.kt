package az.siftoshka.habitube.presentation.screens.movie

import az.siftoshka.habitube.domain.model.Credit

/**
 * The state data class for Movie Screen.
 */
data class MovieCreditsState(
    val isLoading: Boolean = false,
    val credits: Credit? = null,
    val error: String = ""
)