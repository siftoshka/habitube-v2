package az.siftoshka.habitube.presentation.screens.explore

import androidx.annotation.StringRes
import androidx.compose.foundation.gestures.FlingBehavior
import androidx.compose.foundation.gestures.ScrollableDefaults
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import az.siftoshka.habitube.R
import az.siftoshka.habitube.presentation.components.ImageCard
import az.siftoshka.habitube.presentation.components.LongImageCard
import az.siftoshka.habitube.presentation.components.Pager
import az.siftoshka.habitube.presentation.theme.HabitubeV2Theme
import az.siftoshka.habitube.presentation.util.Padding
import com.google.accompanist.systemuicontroller.rememberSystemUiController

/**
 * Composable function of the Explore screen.
 */
@Composable
fun ExploreScreen(
    navController: NavController,
    viewModel: ExploreViewModel = hiltViewModel()
) {
    val systemUiController = rememberSystemUiController()
    systemUiController.setSystemBarsColor(color = MaterialTheme.colors.background)
    val scrollState = rememberScrollState()

    HabitubeV2Theme {
        Surface(color = MaterialTheme.colors.background, modifier = Modifier.fillMaxSize()) {
            Column(
                horizontalAlignment = Alignment.Start,
                modifier = Modifier
                    .verticalScroll(scrollState)
            ) {
                UpcomingMovies()
                Spacer(modifier = Modifier.height(Padding.Large))
                TrendingMovies()
                Spacer(modifier = Modifier.height(Padding.Regular))
                TrendingTvShows()
                Spacer(modifier = Modifier.height(Padding.Regular))
                AirTodayTvShows()
                Spacer(modifier = Modifier.height(Padding.Regular))
            }
        }
    }
}

@Composable
fun UpcomingMovies(
    viewModel: ExploreViewModel = hiltViewModel()
) {
    val upcomingMoviesState = viewModel.exploreUpcomingMoviesState.value

    if (upcomingMoviesState.movies.isNotEmpty()) {
        TitleText(text = R.string.text_upcoming_movies)
        Pager(
            items = upcomingMoviesState.movies,
            itemFraction = 0.85f,
            overshootFraction = 0.5f,
            initialIndex = 1,
            itemSpacing = Padding.Medium,
            modifier = Modifier.fillMaxWidth(),
            contentFactory = {
                LongImageCard(imageUrl = it.backdropPath, title = it.title)
            }
        )
    }
}

@Composable
fun TrendingMovies(
    viewModel: ExploreViewModel = hiltViewModel()
) {
    val trendingMoviesState = viewModel.exploreTrendingMoviesState.value

    if (trendingMoviesState.movies.isNotEmpty()) {
        TitleText(text = R.string.text_trending_movies)
        LazyRow(
            modifier = Modifier.height(150.dp),
            contentPadding = PaddingValues(horizontal = Padding.Regular),
            horizontalArrangement = Arrangement.spacedBy(Padding.Small),
        ) {
            trendingMoviesState.movies.let { movies ->
                items(movies.size) {
                    val movie = movies[it]
                    ImageCard(imageUrl = movie.posterPath, title = movie.title)
                }
            }
        }
    }
}

@Composable
fun TrendingTvShows(
    viewModel: ExploreViewModel = hiltViewModel()
) {
    val trendingTvShowsState = viewModel.exploreTrendingTvShowsState.value

    if (trendingTvShowsState.movies.isNotEmpty()) {
        TitleText(text = R.string.text_trending_shows)
        LazyRow(
            modifier = Modifier.height(150.dp),
            contentPadding = PaddingValues(horizontal = Padding.Regular),
            horizontalArrangement = Arrangement.spacedBy(Padding.Small)
        ) {
            trendingTvShowsState.movies.let { movies ->
                items(movies.size) {
                    val movie = movies[it]
                    ImageCard(imageUrl = movie.posterPath, title = movie.title)
                }
            }
        }
    }
}

@Composable
fun AirTodayTvShows(
    viewModel: ExploreViewModel = hiltViewModel()
) {
    val exploreAirTodayState = viewModel.exploreAirTodayTvShowsState.value

    if (exploreAirTodayState.movies.isNotEmpty()) {
        TitleText(text = R.string.text_air_today)
        LazyRow(
            modifier = Modifier.height(150.dp),
            contentPadding = PaddingValues(horizontal = Padding.Regular),
            horizontalArrangement = Arrangement.spacedBy(Padding.Small)
        ) {
            exploreAirTodayState.movies.let { movies ->
                items(movies.size) {
                    val movie = movies[it]
                    ImageCard(imageUrl = movie.posterPath, title = movie.title)
                }
            }
        }
    }
}

@Composable
fun TitleText(@StringRes text: Int) {
    Text(
        text = stringResource(id = text),
        style = MaterialTheme.typography.h2,
        color = MaterialTheme.colors.onBackground,
        textAlign = TextAlign.Start,
        modifier = Modifier.padding(horizontal = Padding.Regular, vertical = Padding.ExtraSmall)
    )
}