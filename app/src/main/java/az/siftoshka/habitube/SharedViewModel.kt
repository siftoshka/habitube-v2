package az.siftoshka.habitube

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import az.siftoshka.habitube.domain.repository.LocalRepository
import az.siftoshka.habitube.presentation.screens.settings.theme.ThemeType
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

    @Composable
    fun isDarkIconTheme(): Boolean {
        return when (getAppTheme()) {
            ThemeType.CLASSIC -> !isSystemInDarkTheme()
            ThemeType.CYBERPUNK -> !isSystemInDarkTheme()
            ThemeType.ARCTIC -> true
            ThemeType.AMOLED -> false
            ThemeType.NETFLIX -> false
        }
    }
}