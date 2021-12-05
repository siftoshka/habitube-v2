package az.siftoshka.habitube.presentation.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val DarkColorPalette = darkColors(
    primary = ColorPrimaryDark,
    background = DarkGray,
    onBackground = White,
    onPrimary = White,
    surface = MediumGray,
    onSurface = White,
    secondaryVariant = SecondaryDark
)

private val LightColorPalette = lightColors(
    primary = ColorPrimaryLight,
    background = PinkLight,
    onBackground = MediumGray,
    onPrimary = White,
    surface = Pink,
    onSurface = MediumGray,
    secondaryVariant = SecondaryLight
)

@Composable
fun HabitubeV2Theme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable() () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
            colors = colors,
            typography = Typography,
            shapes = Shapes,
            content = content
    )
}