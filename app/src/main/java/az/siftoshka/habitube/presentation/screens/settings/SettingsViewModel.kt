package az.siftoshka.habitube.presentation.screens.settings

import androidx.lifecycle.ViewModel
import az.siftoshka.habitube.R
import az.siftoshka.habitube.domain.repository.LocalRepository
import az.siftoshka.habitube.presentation.screens.settings.sort.SortType
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * [ViewModel] of the Settings Screen.
 */
@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val localRepository: LocalRepository,
) : ViewModel() {

    fun getSortType(): Int {
        return when (localRepository.getSortType()) {
            SortType.RECENTLY -> R.string.text_recently_added
            SortType.FIRST -> R.string.text_added_first
            SortType.TITLE -> R.string.text_sort_title
            SortType.RATING -> R.string.text_sort_rating
            SortType.RELEASE_DESC -> R.string.text_release_desc
            SortType.RELEASE_ASC -> R.string.text_release_asc
        }
    }

    fun isAdultVisible() = localRepository.isAdultVisible()

    fun changeAdultVisibility() = localRepository.setAdultVisibility(!isAdultVisible())

}