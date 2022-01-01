package az.siftoshka.habitube.presentation.screens.discover.list

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
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
import az.siftoshka.habitube.presentation.theme.HabitubeTheme
import az.siftoshka.habitube.presentation.util.Padding
import az.siftoshka.habitube.presentation.util.Screen
import com.google.accompanist.systemuicontroller.rememberSystemUiController

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
    val systemUiController = rememberSystemUiController()
    systemUiController.setSystemBarsColor(color = MaterialTheme.colors.background)

    LaunchedEffect(Unit) {
        viewModel.getDiscoverList(discoverConfiguration)
    }

    val discoverState = viewModel.discoverState.value
    val page = viewModel.discoverListPage.value
    val isLoading = viewModel.discoverState.value.isLoading

    HabitubeTheme {
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
                    contentPadding = PaddingValues(horizontal = Padding.Default, vertical = Padding.Small),
                    horizontalArrangement = Arrangement.spacedBy(Padding.Small),
                    verticalArrangement = Arrangement.spacedBy(Padding.Small)
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
}