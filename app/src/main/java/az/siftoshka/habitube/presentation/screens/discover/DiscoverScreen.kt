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
import az.siftoshka.habitube.presentation.theme.spacing
import az.siftoshka.habitube.presentation.util.NavigationConstants
import az.siftoshka.habitube.presentation.util.Screen

/**
 * Composable function of the Discover Screen.
 */
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DiscoverScreen(
    navController: NavController,
    viewModel: DiscoverViewModel = hiltViewModel()
) {

    Surface(color = MaterialTheme.colors.background, modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = MaterialTheme.spacing.default)
                .verticalScroll(rememberScrollState())
        ) {
            DiscoverConfigurations()
        }
        Column(
            verticalArrangement = Arrangement.Bottom, modifier = Modifier
                .fillMaxWidth()
                .padding(MaterialTheme.spacing.default)
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = MaterialTheme.spacing.extraSmall),
                shape = MaterialTheme.shapes.large,
                backgroundColor = MaterialTheme.colors.primary,
                onClick = {
                    viewModel.updateData().also {
                        navController.currentBackStackEntry?.arguments?.putParcelable(NavigationConstants.PARAM_DISCOVER, it)
                        navController.navigate(Screen.DiscoverListScreen.route)
                    }
                },
            ) {
                Text(
                    text = stringResource(id = R.string.text_discover),
                    color = MaterialTheme.colors.onPrimary,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.h3,
                    modifier = Modifier.padding(MaterialTheme.spacing.medium)
                )
            }
        }
    }
}

@Composable
fun DiscoverConfigurations(viewModel: DiscoverViewModel = hiltViewModel()) {
    MediaOptions()
    Spacer(modifier = Modifier.height(MaterialTheme.spacing.small))
    OptionTitle(title = R.string.text_sort)
    SortOptions()
    OptionTitle(title = R.string.text_years_range)
    OptionSlider(default = 1900f..2022f, value = viewModel.yearRange.value, 0) { viewModel.yearRange.value = it }
    OptionTitle(title = R.string.text_rating_range)
    OptionSlider(default = 4f..10f, value = viewModel.ratingRange.value, 6) { viewModel.ratingRange.value = it }
    OptionTitle(title = R.string.text_genres)
    GenreOptions()
    if (!viewModel.isMovieSelected.value) {
        OptionTitle(title = R.string.text_networks)
        NetworkOptions()
    }
    Spacer(modifier = Modifier.height(128.dp))
}