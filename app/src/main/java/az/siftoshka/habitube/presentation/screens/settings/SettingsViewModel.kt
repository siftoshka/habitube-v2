package az.siftoshka.habitube.presentation.screens.settings

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import az.siftoshka.habitube.R
import az.siftoshka.habitube.domain.repository.LocalRepository
import az.siftoshka.habitube.domain.usecases.local.WatchedMoviesUseCase
import az.siftoshka.habitube.domain.usecases.local.WatchedTvShowUseCase
import az.siftoshka.habitube.presentation.screens.settings.sort.SortType
import az.siftoshka.habitube.presentation.screens.settings.theme.ThemeType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

/**
 * [ViewModel] of the Settings Screen.
 */
@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val watchedMoviesUseCase: WatchedMoviesUseCase,
    private val watchedTvShowUseCase: WatchedTvShowUseCase,
    private val localRepository: LocalRepository,
) : ViewModel() {

    var moviesCount = mutableStateOf(0)
    var showsCount = mutableStateOf(0)

    init {
        watchedMoviesUseCase.getMovies()
            .flowOn(Dispatchers.IO)
            .onEach { result -> moviesCount.value = result.size }
            .launchIn(viewModelScope)
        watchedTvShowUseCase.getShows()
            .flowOn(Dispatchers.IO)
            .onEach { result -> showsCount.value = result.size }
            .launchIn(viewModelScope)
    }

    fun getContentLanguage(): Int {
        return when (localRepository.getContentLanguage()) {
            "en-US" -> R.string.text_lang_english
            "fr-FR" -> R.string.text_lang_french
            "ru-RU" -> R.string.text_lang_russian
            else -> R.string.text_lang_english
        }
    }

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

    fun getThemeType(): Int {
        return when(localRepository.getAppTheme()) {
            ThemeType.CLASSIC -> R.string.theme_classic
            ThemeType.AMOLED -> R.string.theme_amoled
            ThemeType.ARCTIC -> R.string.theme_arctic
            ThemeType.NETFLIX -> R.string.theme_netflix
            ThemeType.CYBERPUNK -> R.string.theme_cyberpunk
        }
    }

    fun isAdultVisible() = localRepository.isAdultVisible()

    fun changeAdultVisibility() = localRepository.setAdultVisibility(!isAdultVisible())
}