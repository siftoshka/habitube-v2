package az.siftoshka.habitube.data.repository

import android.content.SharedPreferences
import androidx.core.content.edit
import az.siftoshka.habitube.domain.repository.LocalRepository
import az.siftoshka.habitube.domain.util.Constants
import az.siftoshka.habitube.presentation.screens.settings.sort.SortType
import az.siftoshka.habitube.presentation.screens.settings.theme.ThemeType
import javax.inject.Inject

/**
 * The implementation of repository class for [SharedPreferences].
 */
class LocalRepositoryImpl @Inject constructor(
    private val preferences: SharedPreferences
) : LocalRepository {

    companion object {
        const val KEY_SETTINGS_LANGUAGE = "key_settings_language"
        const val KEY_SETTINGS_SORT = "key_settings_sort"
        const val KEY_SETTINGS_ADULT = "key_settings_adult"
        const val KEY_VERSION_NAME = "key_version_name"
        const val KEY_APP_THEME = "key_app_theme"
    }

    override fun setContentLanguage(value: String) {
        preferences.edit(commit = true) {
            putString(KEY_SETTINGS_LANGUAGE, value)
        }
    }

    override fun getContentLanguage(): String = preferences.getString(KEY_SETTINGS_LANGUAGE, "en-US").orEmpty()

    override fun setSortType(value: String) {
        preferences.edit(commit = true) {
            putString(KEY_SETTINGS_SORT, value)
        }
    }

    override fun getSortType(): SortType {
        return when (preferences.getString(KEY_SETTINGS_SORT, "RECENTLY")) {
            "RECENTLY" -> SortType.RECENTLY
            "FIRST" -> SortType.FIRST
            "TITLE" -> SortType.TITLE
            "RATING" -> SortType.RATING
            "RELEASE_DESC" -> SortType.RELEASE_DESC
            "RELEASE_ASC" -> SortType.RELEASE_ASC
            else -> SortType.RECENTLY
        }
    }

    override fun setAdultVisibility(value: Boolean) {
        preferences.edit(commit = true) {
            putBoolean(KEY_SETTINGS_ADULT, value)
        }
    }

    override fun isAdultVisible(): Boolean = preferences.getBoolean(KEY_SETTINGS_ADULT, false)

    override fun setVersionName() {
        preferences.edit(commit = true) {
            putString(KEY_VERSION_NAME, Constants.APP_VERSION)
        }
    }

    override fun isUpdateShown(): Boolean {
        return Constants.APP_VERSION == preferences.getString(KEY_VERSION_NAME, "")
    }

    override fun setAppTheme(value: String) {
        preferences.edit(commit = true) {
            putString(KEY_APP_THEME, value)
        }
    }

    override fun getAppTheme(): ThemeType {
        return when (preferences.getString(KEY_APP_THEME, "CLASSIC")) {
            "CLASSIC" -> ThemeType.CLASSIC
            "AMOLED" -> ThemeType.AMOLED
            "ARCTIC" -> ThemeType.ARCTIC
            "NETFLIX" -> ThemeType.NETFLIX
            "CYBERPUNK" -> ThemeType.CYBERPUNK
            else -> ThemeType.CLASSIC
        }
    }
}