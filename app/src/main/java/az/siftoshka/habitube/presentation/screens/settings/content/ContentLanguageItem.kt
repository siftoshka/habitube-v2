package az.siftoshka.habitube.presentation.screens.settings.content

import androidx.annotation.StringRes
import az.siftoshka.habitube.R

/**
 * Item for [ContentLanguageScreen].
 */
class ContentLanguageItem(
    val category: ContentLanguageCategory,
    @StringRes var text: Int,
    val code: String,
)

enum class ContentLanguageCategory {
    ENGLISH,
    FRENCH,
    RUSSIAN
}

val languages = mutableListOf(
    ContentLanguageItem(
        ContentLanguageCategory.ENGLISH,
        R.string.text_lang_english,
        "en-US"
    ),
    ContentLanguageItem(
        ContentLanguageCategory.FRENCH,
        R.string.text_lang_french,
        "fr-FR"
    ),
    ContentLanguageItem(
        ContentLanguageCategory.RUSSIAN,
        R.string.text_lang_russian,
        "ru-RU"
    )
).sortedBy { it.category.name }