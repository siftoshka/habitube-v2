package az.siftoshka.habitube.presentation.screens.discover

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import az.siftoshka.habitube.R
import az.siftoshka.habitube.presentation.screens.discover.components.*
import az.siftoshka.habitube.presentation.theme.HabitubeV2Theme
import az.siftoshka.habitube.presentation.util.NavigationConstants
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
    viewModel: DiscoverViewModel = hiltViewModel()
) {
    val systemUiController = rememberSystemUiController()
    systemUiController.setSystemBarsColor(color = MaterialTheme.colors.background)

    HabitubeV2Theme {
        Surface(color = MaterialTheme.colors.background, modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = Padding.Default)
                    .verticalScroll(rememberScrollState())
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
                    onClick = {
                        viewModel.updateData().also {
                            println(it)
                            navController.currentBackStackEntry?.arguments?.putParcelable(NavigationConstants.PARAM_DISCOVER, it)
                            navController.navigate(Screen.DiscoverListScreen.route)
                        }
                    },
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
fun DiscoverConfigurations(viewModel: DiscoverViewModel = hiltViewModel()) {
    MediaOptions()
    Spacer(modifier = Modifier.height(Padding.Small))
    OptionTitle(title = R.string.text_sort)
    SortOptions()
    OptionTitle(title = R.string.text_years_range)
    OptionSlider(value = 1900f..2022f, 0) { viewModel.yearRange.value = it }
    OptionTitle(title = R.string.text_rating_range)
    OptionSlider(value = 4f..10f, 6) { viewModel.ratingRange.value = it }
    OptionTitle(title = R.string.text_genres)
    GenreOptions()
    Spacer(modifier = Modifier.height(128.dp))
}