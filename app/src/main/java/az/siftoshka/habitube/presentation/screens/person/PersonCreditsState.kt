package az.siftoshka.habitube.presentation.screens.person

import az.siftoshka.habitube.domain.model.PersonCredit

/**
 * The state data class for Person Screen.
 */
data class PersonCreditsState(
    val isLoading: Boolean = false,
    val credits: PersonCredit? = null,
    val error: String = ""
)