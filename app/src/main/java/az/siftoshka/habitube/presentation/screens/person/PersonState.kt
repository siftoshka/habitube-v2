package az.siftoshka.habitube.presentation.screens.person

import az.siftoshka.habitube.domain.model.Person

/**
 * The state data class for Person Screen.
 */
data class PersonState(
    val isLoading: Boolean = false,
    val person: Person? = null,
    val error: String = ""
)