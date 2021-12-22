package az.siftoshka.habitube.data.repository

import android.content.SharedPreferences
import androidx.core.content.edit
import az.siftoshka.habitube.domain.repository.LocalRepository
import az.siftoshka.habitube.presentation.screens.settings.content.ContentLanguageCategory
import az.siftoshka.habitube.presentation.screens.settings.sort.SortType
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
}