package az.siftoshka.habitube.presentation.screens.show

import az.siftoshka.habitube.domain.model.TvShow

/**
 * The state data class for Show Screen.
 */
data class ShowState(
    val isLoading: Boolean = false,
    val show: TvShow? = null,
    val error: String = ""
)