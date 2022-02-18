package az.siftoshka.habitube.presentation.screens.settings.language

import androidx.annotation.StringRes
import az.siftoshka.habitube.R
import az.siftoshka.habitube.domain.util.Language

/**
 * Item for [LanguageScreen].
 */
class LanguageItem(
    val category: LanguageCategory,
    @StringRes var text: Int,
    var code: String? = null,
)

enum class LanguageCategory {
    AUTO,
    ENGLISH,
    AZERBAIJANI,
    SPANISH,
    FRENCH,
    RUSSIAN
}

val languages = mutableListOf(
    LanguageItem(
        LanguageCategory.AUTO,
        R.string.text_lang_automatic
    ),
    LanguageItem(
        LanguageCategory.ENGLISH,
        R.string.text_lang_english,
        Language.ENGLISH.language
    ),
    LanguageItem(
        LanguageCategory.AZERBAIJANI,
        R.string.text_lang_azerbaijani,
        Language.AZERBAIJANI.language
    ),
    /**
     * TODO: Add these languages once the translations will be ready.
     */
//    LanguageItem(
//        LanguageCategory.SPANISH,
//        R.string.text_lang_spanish,
//        Language.SPANISH.language
//    ),
//    LanguageItem(
//        LanguageCategory.FRENCH,
//        R.string.text_lang_french,
//        Language.FRENCH.language
//    ),
    LanguageItem(
        LanguageCategory.RUSSIAN,
        R.string.text_lang_russian,
        Language.RUSSIAN.language
    )
).sortedBy { it.code }