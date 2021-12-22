package az.siftoshka.habitube.presentation.screens.settings.sort

import androidx.lifecycle.ViewModel
import az.siftoshka.habitube.domain.repository.LocalRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * [ViewModel] of the Sort Screen.
 */
@HiltViewModel
class SortViewModel @Inject constructor(
    private val localRepository: LocalRepository
) : ViewModel() {

    fun setSortType(value: String) = localRepository.setSortType(value)

    fun getSortType() = localRepository.getSortType()
}