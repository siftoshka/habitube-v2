package az.siftoshka.habitube.presentation.screens.discover

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavController
import az.siftoshka.habitube.R
import az.siftoshka.habitube.presentation.screens.discover.components.MediaOptions
import az.siftoshka.habitube.presentation.screens.discover.components.OptionTitle
import az.siftoshka.habitube.presentation.screens.discover.components.SortOptions
import az.siftoshka.habitube.presentation.theme.HabitubeV2Theme
import az.siftoshka.habitube.presentation.util.Padding
import az.siftoshka.habitube.presentation.util.Screen
import com.google.accompanist.systemuicontroller.rememberSystemUiController

/**
 * Composable function of the Discover Screen.
 */
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DiscoverScreen(
    navController: NavController,
) {
    val systemUiController = rememberSystemUiController()
    systemUiController.setSystemBarsColor(color = MaterialTheme.colors.background)

    HabitubeV2Theme {
        Surface(color = MaterialTheme.colors.background, modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = Padding.Default)
            ) {
                DiscoverConfigurations()
            }
            Column(
                verticalArrangement = Arrangement.Bottom, modifier = Modifier
                    .fillMaxWidth()
                    .padding(Padding.Default)
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = Padding.ExtraSmall),
                    shape = MaterialTheme.shapes.large,
                    backgroundColor = MaterialTheme.colors.primary,
                    onClick = { navController.navigate(Screen.DiscoverListScreen.route) },
                ) {
                    Text(
                        text = stringResource(id = R.string.nav_discover),
                        color = MaterialTheme.colors.onPrimary,
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.h3,
                        modifier = Modifier.padding(Padding.Medium)
                    )
                }
            }
        }
    }
}

@Composable
fun DiscoverConfigurations() {
    MediaOptions()
    Spacer(modifier = Modifier.height(Padding.Small))
    OptionTitle(title = R.string.text_sort)
    SortOptions()
}