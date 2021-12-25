package az.siftoshka.habitube.presentation.screens.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import az.siftoshka.habitube.R
import az.siftoshka.habitube.domain.util.Constants.PAGE_SIZE
import az.siftoshka.habitube.domain.util.isInternetAvailable
import az.siftoshka.habitube.presentation.components.Pager
import az.siftoshka.habitube.presentation.components.TopAppBar
import az.siftoshka.habitube.presentation.components.UpdateDialog
import az.siftoshka.habitube.presentation.components.image.ImageCard
import az.siftoshka.habitube.presentation.components.image.LongImageCard
import az.siftoshka.habitube.presentation.components.screen.LoadingScreen
import az.siftoshka.habitube.presentation.components.screen.NoConnectionScreen
import az.siftoshka.habitube.presentation.components.text.TitleText
import az.siftoshka.habitube.presentation.theme.HabitubeV2Theme
import az.siftoshka.habitube.presentation.util.Padding
import az.siftoshka.habitube.presentation.util.Screen
import com.google.accompanist.systemuicontroller.rememberSystemUiController

/**
 * Composable function of the Home Screen.
 */
@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val systemUiController = rememberSystemUiController()
    systemUiController.setSystemBarsColor(color = MaterialTheme.colors.background)
    val scrollState = rememberScrollState()
    val context = LocalContext.current

    val updateState = viewModel.updateState
    val dialogState = remember { mutableStateOf(false) }
    val isLoading = viewModel.exploreUpcomingMoviesState.value.isLoading &&
            viewModel.exploreTrendingMoviesState.value.isLoading &&
            viewModel.exploreTrendingTvShowsState.value.isLoading &&
            viewModel.exploreAirTodayTvShowsState.value.isLoading

    HabitubeV2Theme {
        Surface(color = MaterialTheme.colors.background, modifier = Modifier.fillMaxSize()) {
            Column(modifier = Modifier.fillMaxSize()) {
                TopAppBar(
                    title = R.string.app_name,
                    icon = R.drawable.ic_launch_icon
                ) {
                    dialogState.value = true
                }
                if (isLoading) LoadingScreen()
                if (!context.isInternetAvailable()) NoConnectionScreen()
                Column(
                    horizontalAlignment = Alignment.Start,
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(scrollState)
                ) {
                    viewModel.isUpdateShown()
                    if (updateState.value) { dialogState.value = true }
                    UpdateDialog(
                        title = R.string.text_update_title,
                        text = R.string.text_update_description,
                        textButton = R.string.text_update_button,
                        state = dialogState
                    ) {
                        dialogState.value = false
                        viewModel.setUpdateShown()
                    }
                    UpcomingMovies(navController)
                    Spacer(modifier = Modifier.height(Padding.Large))
                    TrendingMovies(navController)
                    Spacer(modifier = Modifier.height(Padding.Regular))
                    TrendingTvShows(navController)
                    Spacer(modifier = Modifier.height(Padding.Regular))
                    AirTodayTvShows(navController)
                    Spacer(modifier = Modifier.height(Padding.Regular))
                }
            }
        }
    }
}

@Composable
fun UpcomingMovies(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val upcomingMoviesState = viewModel.exploreUpcomingMoviesState.value

    if (upcomingMoviesState.media.isNotEmpty()) {
        TitleText(text = R.string.text_upcoming, secondary = R.string.text_movies)
        Pager(
            width = 320.dp,
            context = { lazyListState ->
                LazyRow(
                    state = lazyListState,
                    contentPadding = PaddingValues(horizontal = Padding.Default),
                    horizontalArrangement = Arrangement.spacedBy(Padding.Small)
                ) {
                    itemsIndexed(upcomingMoviesState.media) { index, movie ->
                        LongImageCard(imageUrl = movie.backdropPath, title = movie.title) {
                            navController.navigate(Screen.MovieScreen.route + "/${movie.id}")
                        }
                    }
                }
            }
        )
    }
}

@Composable
fun TrendingMovies(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val trendingMoviesState = viewModel.exploreTrendingMoviesState.value
    val page = viewModel.trendingMoviesPage.value

    if (trendingMoviesState.media.isNotEmpty()) {
        TitleText(text = R.string.text_trending, secondary = R.string.text_movies)
        LazyRow(
            modifier = Modifier.height(150.dp),
            contentPadding = PaddingValues(horizontal = Padding.Default),
            horizontalArrangement = Arrangement.spacedBy(Padding.Small),
        ) {
            itemsIndexed(items = trendingMoviesState.media) { index, movie ->
                viewModel.onChangeTrendingMoviesPosition(index)
                if ((index + 1) >= (page * PAGE_SIZE)) {
                    viewModel.getMoreTrendingMovies()
                }
                ImageCard(imageUrl = movie.posterPath, title = movie.title) {
                    navController.navigate(Screen.MovieScreen.route + "/${movie.id}")
                }
            }
        }
    }
}

@Composable
fun TrendingTvShows(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val trendingTvShowsState = viewModel.exploreTrendingTvShowsState.value
    val page = viewModel.trendingTvShowsPage.value

    if (trendingTvShowsState.media.isNotEmpty()) {
        TitleText(text = R.string.text_trending, secondary = R.string.text_shows)
        LazyRow(
            modifier = Modifier.height(150.dp),
            contentPadding = PaddingValues(horizontal = Padding.Default),
            horizontalArrangement = Arrangement.spacedBy(Padding.Small)
        ) {
            itemsIndexed(items = trendingTvShowsState.media) { index, show ->
                viewModel.onChangeTrendingTvShowsPosition(index)
                if ((index + 1) >= (page * PAGE_SIZE)) {
                    viewModel.getMoreTrendingTvShows()
                }
                ImageCard(imageUrl = show.posterPath, title = show.title) {
                    navController.navigate(Screen.TvShowScreen.route + "/${show.id}")
                }
            }
        }
    }
}

@Composable
fun AirTodayTvShows(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val exploreAirTodayState = viewModel.exploreAirTodayTvShowsState.value
    val page = viewModel.airTodayTvShowsPage.value

    if (exploreAirTodayState.media.isNotEmpty()) {
        TitleText(text = R.string.text_air_today, secondary = R.string.text_shows)
        LazyRow(
            modifier = Modifier.height(150.dp),
            contentPadding = PaddingValues(horizontal = Padding.Default),
            horizontalArrangement = Arrangement.spacedBy(Padding.Small)
        ) {
            itemsIndexed(items = exploreAirTodayState.media) { index, show ->
                viewModel.onChangeAirTodayTvShowsPosition(index)
                if ((index + 1) >= (page * PAGE_SIZE)) {
                    viewModel.getMoreAirTodayTvShows()
                }
                ImageCard(imageUrl = show.posterPath, title = show.title) {
                    navController.navigate(Screen.TvShowScreen.route + "/${show.id}")
                }
            }
        }
    }
}