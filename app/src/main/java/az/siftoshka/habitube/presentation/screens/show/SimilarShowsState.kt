package az.siftoshka.habitube.presentation.screens.show

import az.siftoshka.habitube.domain.model.MediaLite

/**
 * The state data class for Show Screen.
 */
data class SimilarShowsState(
    val isLoading: Boolean = false,
    val shows: List<MediaLite> = emptyList(),
    val error: String = ""
)