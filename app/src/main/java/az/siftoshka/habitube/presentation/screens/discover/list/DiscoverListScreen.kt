package az.siftoshka.habitube.presentation.screens.discover.list

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import az.siftoshka.habitube.R
import az.siftoshka.habitube.domain.model.DiscoverConfiguration
import az.siftoshka.habitube.domain.util.Constants
import az.siftoshka.habitube.presentation.components.TopAppBar
import az.siftoshka.habitube.presentation.components.image.LongImageCard
import az.siftoshka.habitube.presentation.components.screen.LoadingScreen
import az.siftoshka.habitube.presentation.components.screen.NothingScreen
import az.siftoshka.habitube.presentation.theme.spacing
import az.siftoshka.habitube.presentation.util.Screen

/**
 * Composable function of the Discover List Screen.
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DiscoverListScreen(
    navController: NavController,
    discoverConfiguration: DiscoverConfiguration?,
    viewModel: DiscoverListViewModel = hiltViewModel()
) {

    LaunchedEffect(Unit) {
        viewModel.getDiscoverList(discoverConfiguration)
    }

    val discoverState = viewModel.discoverState.value
    val page = viewModel.discoverListPage.value
    val isLoading = viewModel.discoverState.value.isLoading

    Surface(color = MaterialTheme.colors.background, modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {
            TopAppBar(
                title = R.string.nav_discover,
                icon = R.drawable.ic_back,
            ) { navController.popBackStack() }
            if (isLoading) LoadingScreen()
            if (discoverState.media.isEmpty()) NothingScreen()
            LazyVerticalGrid(
                cells = GridCells.Fixed(2),
                contentPadding = PaddingValues(horizontal = MaterialTheme.spacing.default, vertical = MaterialTheme.spacing.small),
                horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small),
                verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small)
            ) {
                itemsIndexed(items = discoverState.media) { index, media ->
                    viewModel.onChangeDiscoverListPosition(index)
                    if ((index + 1) >= (page * Constants.PAGE_SIZE)) {
                        viewModel.getMoreDiscoverList(discoverConfiguration)
                    }
                    if (discoverConfiguration?.isMovieSelected == true) {
                        LongImageCard(imageUrl = media.posterPath, title = media.title) {
                            navController.navigate(Screen.MovieScreen.route + "/${media.id}")
                        }
                    } else {
                        LongImageCard(imageUrl = media.posterPath, title = media.name) {
                            navController.navigate(Screen.TvShowScreen.route + "/${media.id}")
                        }
                    }
                }
            }
        }
    }
}