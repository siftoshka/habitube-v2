package az.siftoshka.habitube.presentation.screens.movie

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import az.siftoshka.habitube.R
import az.siftoshka.habitube.domain.util.*
import az.siftoshka.habitube.presentation.components.DetailsCard
import az.siftoshka.habitube.presentation.components.image.Avatar
import az.siftoshka.habitube.presentation.components.image.BackgroundImage
import az.siftoshka.habitube.presentation.components.image.ImageCard
import az.siftoshka.habitube.presentation.components.image.VideoCard
import az.siftoshka.habitube.presentation.components.text.DetailText
import az.siftoshka.habitube.presentation.components.text.DetailTitle
import az.siftoshka.habitube.presentation.theme.HabitubeV2Theme
import az.siftoshka.habitube.presentation.util.Padding
import az.siftoshka.habitube.presentation.util.Screen
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import java.lang.Float.min

/**
 * Composable function of the Movie Screen.
 */
@Composable
fun MovieScreen(
    navController: NavController,
    viewModel: MovieViewModel = hiltViewModel()
) {
    val systemUiController = rememberSystemUiController()
    systemUiController.setSystemBarsColor(color = MaterialTheme.colors.background)
    val scrollState = rememberScrollState()

    val movieState = viewModel.movieState.value

    HabitubeV2Theme {
        Surface(color = MaterialTheme.colors.background, modifier = Modifier.fillMaxSize()) {
            Column(modifier = Modifier.fillMaxSize()) {
                if (movieState.isLoading) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        CircularProgressIndicator(color = MaterialTheme.colors.primary)
                    }
                }
                MainBoard(scrollState, navController)
                InfoBoard(scrollState, navController)
            }
        }
    }
}

@Composable
fun MainBoard(
    scrollState: ScrollState,
    navController: NavController,
    viewModel: MovieViewModel = hiltViewModel()
) {
    val movie = viewModel.movieState.value.movie

    Column {
        BackgroundImage(
            modifier = Modifier
                .fillMaxWidth()
                .height((200 - (scrollState.value * 0.15f)).dp)
                .graphicsLayer {
                    alpha = min(1f, 1 - (scrollState.value / 600f))
                    translationY = -scrollState.value * 0.1f
                },
            imageUrl = movie?.backdropPath
        ) { navController.popBackStack() }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = Padding.Default, vertical = Padding.Small)
        ) {
            ImageCard(
                imageUrl = movie?.posterPath,
                title = movie?.title,
                indication = null
            ) {}
            Column(modifier = Modifier.padding(horizontal = Padding.Small)) {
                Text(
                    text = buildAnnotatedString {
                        append("${movie?.title}")
                        append(
                            AnnotatedString(
                                " (${movie?.releaseDate?.onlyYear()})",
                                spanStyle = SpanStyle(color = MaterialTheme.colors.secondaryVariant, fontWeight = FontWeight.Light)
                            )
                        )
                    },
                    style = MaterialTheme.typography.h3,
                    color = MaterialTheme.colors.onBackground,
                    textAlign = TextAlign.Start,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(Padding.ExtraSmall))
                Text(
                    text = buildAnnotatedString {
                        append(stringResource(id = R.string.text_rating))
                        append(
                            AnnotatedString(
                                ": ${movie?.voteAverage} (${movie?.voteCount})",
                                spanStyle = SpanStyle(color = MaterialTheme.colors.secondaryVariant, fontWeight = FontWeight.Normal)
                            )
                        )
                    },
                    style = MaterialTheme.typography.h5,
                    color = MaterialTheme.colors.onBackground,
                    textAlign = TextAlign.Start,
                )
                Text(
                    text = buildAnnotatedString {
                        append(stringResource(id = R.string.text_runtime))
                        append(
                            AnnotatedString(
                                ": ${movie?.runtime} ${stringResource(id = R.string.text_minutes)}",
                                spanStyle = SpanStyle(color = MaterialTheme.colors.secondaryVariant, fontWeight = FontWeight.Normal)
                            )
                        )
                    },
                    style = MaterialTheme.typography.h5,
                    color = MaterialTheme.colors.onBackground,
                    textAlign = TextAlign.Start,
                )
            }
        }
    }
}

@Composable
fun InfoBoard(
    scrollState: ScrollState,
    navController: NavController,
    viewModel: MovieViewModel = hiltViewModel()
) {
    val movieState = viewModel.movieState.value
    val videosState = viewModel.videosState.value
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(Padding.Default)
    ) {
        if (videosState.videos.isNotEmpty()) {
            DetailTitle(text = R.string.text_videos)
            DetailsCard {
                LazyRow(
                    modifier = Modifier.padding(Padding.Medium),
                    horizontalArrangement = Arrangement.spacedBy(Padding.Small)
                ) {
                    videosState.videos.let { videos ->
                        items(videos.size) {
                            val video = videos[it]
                            val imageUrl = video.site?.takeVideoImageUrl(video.key)
                            VideoCard(imageUrl = imageUrl, title = video.name) {
                                context.openVideo(video.site, video.key)
                            }
                        }
                    }
                }
            }
        }
        movieState.movie?.let { movie ->
            Spacer(modifier = Modifier.height(Padding.Medium))
            DetailTitle(text = R.string.text_description)
            DetailsCard {
                Text(
                    text = movie.overview.orEmpty(),
                    style = MaterialTheme.typography.body1,
                    color = MaterialTheme.colors.onSurface,
                    textAlign = TextAlign.Start,
                    modifier = Modifier.padding(Padding.Medium)
                )
            }
            Spacer(modifier = Modifier.height(Padding.Medium))
            DetailTitle(text = R.string.text_details)
            DetailsCard {
                Column(modifier = Modifier.padding(Padding.Medium)) {
                    DetailText(name = R.string.text_original_title, detail = ": ${movie.originalTitle}")
                    DetailText(name = R.string.text_budget, detail = ": $${movie.budget.toString().moneyFormat()}")
                    DetailText(name = R.string.text_revenue, detail = ": $${movie.revenue.toString().moneyFormat()}")
                    val languages = movie.spokenLanguages?.map { it.englishName }?.toFormattedString()
                    DetailText(name = R.string.text_spoken_languages, detail = ": $languages")
                    val genres = movie.genres?.map { it.name }?.toFormattedString()
                    DetailText(name = R.string.text_genres, detail = ": $genres")
                }
            }
            Spacer(modifier = Modifier.height(Padding.Medium))
            Cast()
            Spacer(modifier = Modifier.height(Padding.Medium))
            Crew()
            Spacer(modifier = Modifier.height(Padding.Medium))
            SimilarMovies(navController)
            Spacer(modifier = Modifier.height(Padding.Medium))
        }
    }
}

@Composable
fun Cast(
    viewModel: MovieViewModel = hiltViewModel()
) {
    val creditState = viewModel.creditsState.value

    if (creditState.credits?.cast?.isNotEmpty() == true) {
        DetailTitle(text = R.string.text_cast)
        DetailsCard {
            LazyRow(
                modifier = Modifier.padding(Padding.Medium),
                horizontalArrangement = Arrangement.spacedBy(Padding.Small)
            ) {
                creditState.credits.cast.let { cast ->
                    items(cast.size) {
                        val actor = cast[it]
                        Avatar(imageUrl = actor.profilePath, title = actor.name, secondary = actor.character) { }
                    }
                }
            }
        }
    }
}

@Composable
fun Crew(
    viewModel: MovieViewModel = hiltViewModel()
) {
    val creditState = viewModel.creditsState.value

    if (creditState.credits?.crew?.isNotEmpty() == true) {
        DetailTitle(text = R.string.text_crew)
        DetailsCard {
            LazyRow(
                modifier = Modifier.padding(Padding.Medium),
                horizontalArrangement = Arrangement.spacedBy(Padding.Small)
            ) {
                creditState.credits.crew.let { crew ->
                    items(crew.size) {
                        val actor = crew[it]
                        Avatar(imageUrl = actor.profilePath, title = actor.name, secondary = actor.knownForDepartment) {}
                    }
                }
            }
        }
    }
}

@Composable
fun SimilarMovies(
    navController: NavController,
    viewModel: MovieViewModel = hiltViewModel()
) {
    val similarState = viewModel.similarState.value
    val page = viewModel.similarMoviesPage.value

    if (similarState.movies.isNotEmpty()) {
        DetailTitle(text = R.string.text_similar)
        DetailsCard {
            LazyRow(
                modifier = Modifier.padding(Padding.Medium),
                horizontalArrangement = Arrangement.spacedBy(Padding.Small)
            ) {
                itemsIndexed(items = similarState.movies) { index, movie ->
                    viewModel.onChangePosition(index)
                    if ((index + 1) >= (page * Constants.PAGE_SIZE)) {
                        viewModel.getMoreSimilarMovies()
                    }
                    ImageCard(imageUrl = movie.posterPath, title = movie.title) {
                        navController.navigate(Screen.MovieScreen.route + "/${movie.id}")
                    }
                }
            }
        }
    }
}