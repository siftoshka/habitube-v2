package az.siftoshka.habitube

import androidx.lifecycle.ViewModel
import az.siftoshka.habitube.domain.repository.LocalRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * Shared [ViewModel] of the entire app.
 */
@HiltViewModel
class SharedViewModel @Inject constructor(
    private val localRepository: LocalRepository
) : ViewModel() {

    fun getAppTheme() = localRepository.getAppTheme()

    fun updateTheme(value: String) {
        localRepository.setAppTheme(value)
    }
}