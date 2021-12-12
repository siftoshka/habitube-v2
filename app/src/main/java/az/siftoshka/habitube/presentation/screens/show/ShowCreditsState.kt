package az.siftoshka.habitube.presentation.screens.show

import az.siftoshka.habitube.domain.model.Credit

/**
 * The state data class for Show Screen.
 */
data class ShowCreditsState(
    val isLoading: Boolean = false,
    val credits: Credit? = null,
    val error: String = ""
)