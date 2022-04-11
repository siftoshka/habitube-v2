package az.siftoshka.habitube.presentation.screens.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import az.siftoshka.habitube.R
import az.siftoshka.habitube.domain.util.Constants.PAGE_SIZE
import az.siftoshka.habitube.domain.util.isInternetAvailable
import az.siftoshka.habitube.presentation.components.Pager
import az.siftoshka.habitube.presentation.components.TopAppBar
import az.siftoshka.habitube.presentation.components.image.ImageCard
import az.siftoshka.habitube.presentation.components.image.LongImageCard
import az.siftoshka.habitube.presentation.components.screen.EmptyScreen
import az.siftoshka.habitube.presentation.components.screen.LoadingScreen
import az.siftoshka.habitube.presentation.components.screen.NoConnectionScreen
import az.siftoshka.habitube.presentation.screens.home.components.TitleText
import az.siftoshka.habitube.presentation.screens.home.dialog.UpdateDialog
import az.siftoshka.habitube.presentation.theme.spacing
import az.siftoshka.habitube.presentation.util.Screen

/**
 * Composable function of the Home Screen.
 */
@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val scrollState = rememberScrollState()
    val context = LocalContext.current

    val updateState = viewModel.updateState
    val dialogState = remember { mutableStateOf(false) }
    val isLoading = viewModel.exploreUpcomingMoviesState.value.isLoading &&
            viewModel.exploreTrendingMoviesState.value.isLoading &&
            viewModel.exploreTrendingTvShowsState.value.isLoading &&
            viewModel.exploreAirTodayTvShowsState.value.isLoading
    val isEmpty = viewModel.exploreUpcomingMoviesState.value.media.isEmpty() &&
            viewModel.exploreTrendingMoviesState.value.media.isEmpty() &&
            viewModel.exploreTrendingTvShowsState.value.media.isEmpty() &&
            viewModel.exploreAirTodayTvShowsState.value.media.isEmpty()

    Surface(color = MaterialTheme.colors.background, modifier = Modifier.fillMaxSize()) {
        Scaffold(
            floatingActionButton = { FloatingSearchButton(navController) },
            floatingActionButtonPosition = FabPosition.End
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                TopAppBar(title = R.string.app_name, icon = R.drawable.ic_launch_icon) { dialogState.value = true }
                if (isLoading) LoadingScreen()
                if (!context.isInternetAvailable()) NoConnectionScreen { viewModel.updateScreen() }
                if (isEmpty) EmptyScreen()
                Column(
                    horizontalAlignment = Alignment.Start,
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(scrollState)
                ) {
                    viewModel.isUpdateShown()
                    if (updateState.value) {
                        dialogState.value = true
                    }
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
                    Spacer(modifier = Modifier.height(MaterialTheme.spacing.large))
                    TrendingMovies(navController)
                    Spacer(modifier = Modifier.height(MaterialTheme.spacing.regular))
                    TrendingTvShows(navController)
                    Spacer(modifier = Modifier.height(MaterialTheme.spacing.regular))
                    AirTodayTvShows(navController)
                    Spacer(modifier = Modifier.height(MaterialTheme.spacing.regular))
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
                    contentPadding = PaddingValues(horizontal = MaterialTheme.spacing.default),
                    horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small)
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
            contentPadding = PaddingValues(horizontal = MaterialTheme.spacing.default),
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small),
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
            contentPadding = PaddingValues(horizontal = MaterialTheme.spacing.default),
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small)
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
            contentPadding = PaddingValues(horizontal = MaterialTheme.spacing.default),
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small)
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

@Composable
fun FloatingSearchButton(navController: NavController) {
    FloatingActionButton(
        onClick = { navController.navigate(Screen.SearchScreen.route) },
        shape = MaterialTheme.shapes.large,
        backgroundColor = MaterialTheme.colors.primary,
        modifier = Modifier.size(width = 90.dp, height = 50.dp)
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_search),
            contentDescription = stringResource(id = R.string.text_search),
            tint = Color.White,
            modifier = Modifier.size(25.dp)
        )
    }
}