package az.siftoshka.habitube.presentation.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import az.siftoshka.habitube.SharedViewModel
import az.siftoshka.habitube.presentation.screens.settings.theme.ThemeType
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun HabitubeTheme(viewModel: SharedViewModel, content: @Composable () -> Unit) {

    viewModel.getAppTheme()

    val colors = if (isSystemInDarkTheme()) {
        when (viewModel.getAppTheme()) {
            ThemeType.CLASSIC -> ClassicDarkPalette
            ThemeType.AMOLED -> AmoledPalette
            ThemeType.ARCTIC -> ArcticBluePalette
            ThemeType.NETFLIX -> NetflixPalette
            ThemeType.CYBERPUNK -> CyberpunkDarkPalette
        }
    } else {
        when (viewModel.getAppTheme()) {
            ThemeType.CLASSIC -> ClassicLightPalette
            ThemeType.AMOLED -> AmoledPalette
            ThemeType.ARCTIC -> ArcticBluePalette
            ThemeType.NETFLIX -> NetflixPalette
            ThemeType.CYBERPUNK -> CyberpunkLightPalette
        }
    }

    CompositionLocalProvider(LocalSpacing provides Spacing()) {
        val systemUiController = rememberSystemUiController()
        systemUiController.setSystemBarsColor(color = MaterialTheme.colors.background, darkIcons = viewModel.isDarkIconTheme())

        MaterialTheme(
            colors = colors,
            typography = Typography,
            shapes = Shapes,
            content = content
        )
    }
}