package az.siftoshka.habitube.presentation.screens.settings.sort

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import az.siftoshka.habitube.domain.repository.LocalRepository
import az.siftoshka.habitube.domain.usecases.local.PlannedMoviesUseCase
import az.siftoshka.habitube.domain.usecases.local.PlannedTvShowUseCase
import az.siftoshka.habitube.domain.usecases.local.WatchedMoviesUseCase
import az.siftoshka.habitube.domain.usecases.local.WatchedTvShowUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
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