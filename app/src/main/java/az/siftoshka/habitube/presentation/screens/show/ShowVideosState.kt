package az.siftoshka.habitube.presentation.screens.show

import az.siftoshka.habitube.domain.model.Video

/**
 * The state data class for Show Screen.
 */
data class ShowVideosState(
    val isLoading: Boolean = false,
    val videos: List<Video> = emptyList(),
    val error: String = ""
)