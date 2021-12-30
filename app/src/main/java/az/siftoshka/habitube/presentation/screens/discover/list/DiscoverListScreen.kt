package az.siftoshka.habitube.presentation.screens.discover.list

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import az.siftoshka.habitube.R
import az.siftoshka.habitube.presentation.components.TopAppBar
import az.siftoshka.habitube.presentation.theme.HabitubeV2Theme
import com.google.accompanist.systemuicontroller.rememberSystemUiController

/**
 * Composable function of the Discover List Screen.
 */
@Composable
fun DiscoverListScreen(
    navController: NavController,
) {
    val systemUiController = rememberSystemUiController()
    systemUiController.setSystemBarsColor(color = MaterialTheme.colors.background)

    HabitubeV2Theme {
        Surface(color = MaterialTheme.colors.background, modifier = Modifier.fillMaxSize()) {
            Column(modifier = Modifier.fillMaxSize()) {
                TopAppBar(
                    title = R.string.nav_discover,
                    icon = R.drawable.ic_back,
                ) { navController.popBackStack() }
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}