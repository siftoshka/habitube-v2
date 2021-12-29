package az.siftoshka.habitube.presentation.screens.discover

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import az.siftoshka.habitube.presentation.theme.HabitubeV2Theme
import com.google.accompanist.systemuicontroller.rememberSystemUiController

/**
 * Composable function of the Discover Screen.
 */
@Composable
fun DiscoverScreen(
    navController: NavController,
) {
    val systemUiController = rememberSystemUiController()
    systemUiController.setSystemBarsColor(color = MaterialTheme.colors.background)

    HabitubeV2Theme {
        Surface(color = MaterialTheme.colors.background, modifier = Modifier.fillMaxSize()) {
            Column(modifier = Modifier.fillMaxSize()) {
            }
        }
    }
}