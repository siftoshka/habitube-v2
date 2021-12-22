package az.siftoshka.habitube.presentation.screens.settings.content

import androidx.lifecycle.ViewModel
import az.siftoshka.habitube.domain.repository.LocalRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * [ViewModel] of the Content Language Screen.
 */
@HiltViewModel
class ContentLanguageViewModel @Inject constructor(
    private val localRepository: LocalRepository
) : ViewModel() {

    fun setContentLanguage(value: String) = localRepository.setContentLanguage(value)

    fun getContentLanguage() = localRepository.getContentLanguage()
}