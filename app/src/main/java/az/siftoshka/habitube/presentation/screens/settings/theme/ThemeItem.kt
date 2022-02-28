package az.siftoshka.habitube.presentation.screens.settings.theme

import androidx.annotation.StringRes
import az.siftoshka.habitube.R

/**
 * Item for [ThemeScreen].
 */
class ThemeItem(
    val category: ThemeType,
    @StringRes var text: Int,
)

enum class ThemeType {
    CLASSIC,
    AMOLED,
    ARCTIC,
    NETFLIX,
    CYBERPUNK
}

val themes = mutableListOf(
    ThemeItem(
        ThemeType.CLASSIC,
        R.string.theme_classic_full
    ),
    ThemeItem(
        ThemeType.AMOLED,
        R.string.theme_amoled
    ),
    ThemeItem(
        ThemeType.ARCTIC,
        R.string.theme_arctic
    ),
    ThemeItem(
       ThemeType.NETFLIX,
       R.string.theme_netflix
    ),
    ThemeItem(
        ThemeType.CYBERPUNK,
        R.string.theme_cyberpunk_full
    )
)