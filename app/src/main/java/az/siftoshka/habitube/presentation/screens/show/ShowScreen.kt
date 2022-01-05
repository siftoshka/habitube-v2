package az.siftoshka.habitube.presentation.screens.show

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import az.siftoshka.habitube.presentation.components.SeasonCard
import az.siftoshka.habitube.presentation.components.StoreButton
import az.siftoshka.habitube.presentation.components.image.Avatar
import az.siftoshka.habitube.presentation.components.image.BackgroundImage
import az.siftoshka.habitube.presentation.components.image.ImageCard
import az.siftoshka.habitube.presentation.components.image.VideoCard
import az.siftoshka.habitube.presentation.components.screen.LoadingScreen
import az.siftoshka.habitube.presentation.components.text.DetailText
import az.siftoshka.habitube.presentation.components.text.DetailTitle
import az.siftoshka.habitube.presentation.components.text.ExpandableText
import az.siftoshka.habitube.presentation.theme.HabitubeTheme
import az.siftoshka.habitube.presentation.util.Padding
import az.siftoshka.habitube.presentation.util.Screen
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.gowtham.ratingbar.RatingBar
import com.gowtham.ratingbar.RatingBarStyle
import com.gowtham.ratingbar.StepSize
import java.lang.Float.min

/**
 * Composable function of the Show Screen.
 */
@Composable
fun ShowScreen(
    navController: NavController,
    viewModel: ShowViewModel = hiltViewModel()
) {
    val systemUiController = rememberSystemUiController()
    systemUiController.setSystemBarsColor(color = MaterialTheme.colors.background)
    val scrollState = rememberScrollState()

    val showState = viewModel.showState.value

    HabitubeTheme {
        Surface(color = MaterialTheme.colors.background, modifier = Modifier.fillMaxSize()) {
            Column(modifier = Modifier.fillMaxSize()) {
                if (showState.isLoading) LoadingScreen()
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
    viewModel: ShowViewModel = hiltViewModel()
) {
    val show = viewModel.showState.value.show

    Column {
        BackgroundImage(
            modifier = Modifier
                .fillMaxWidth()
                .height((200 - (scrollState.value * 0.15f)).dp)
                .graphicsLayer {
                    alpha = min(1f, 1 - (scrollState.value / 600f))
                    translationY = -scrollState.value * 0.1f
                },
            imageUrl = show?.backdropPath
        ) { navController.popBackStack() }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = Padding.Default, vertical = Padding.Small)
        ) {
            ImageCard(
                imageUrl = show?.posterPath,
                title = show?.name,
                indication = null
            ) {}
            Column(
                modifier = Modifier
                    .padding(horizontal = Padding.Small)
                    .height(150.dp)
            )
            {
                Text(
                    text = buildAnnotatedString {
                        append("${show?.name}")
                        append(
                            AnnotatedString(
                                " (${show?.firstAirDate?.onlyYear()})",
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
                                ": ${show?.voteAverage} (${show?.voteCount})",
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
                                ": ${show?.episodeRunTime?.toFormattedString()} ${stringResource(id = R.string.text_minutes)}",
                                spanStyle = SpanStyle(color = MaterialTheme.colors.secondaryVariant, fontWeight = FontWeight.Normal)
                            )
                        )
                    },
                    style = MaterialTheme.typography.h5,
                    color = MaterialTheme.colors.onBackground,
                    textAlign = TextAlign.Start,
                    modifier = Modifier.weight(1f)
                )
                Row {
                    StoreButton(
                        inActiveText = stringResource(id = R.string.text_add_rating),
                        activeText = stringResource(id = R.string.text_rated, viewModel.rating.value),
                        icon = R.drawable.ic_star,
                        isMediaExist = viewModel.isWatched
                    ) { isNotWatched ->
                        if (isNotWatched) {
                            viewModel.addWatched(viewModel.rating.value)
                        } else {
                            viewModel.deleteWatched()
                        }
                    }
                    StoreButton(
                        inActiveText = stringResource(id = R.string.text_planning_watch),
                        activeText = stringResource(id = R.string.text_planning_watch),
                        icon = R.drawable.ic_planning,
                        isMediaExist = viewModel.isPlanned
                    ) { isNotPlanned ->
                        if (isNotPlanned) {
                            viewModel.addPlanned()
                        } else {
                            viewModel.deletePlanned()
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun InfoBoard(
    scrollState: ScrollState,
    navController: NavController,
    viewModel: ShowViewModel = hiltViewModel()
) {
    val showState = viewModel.showState.value
    val videosState = viewModel.videosState.value
    val ratingState = viewModel.rating
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
    ) {
        if (viewModel.isWatched.value) {
            Column(Modifier.padding(horizontal = Padding.Default)) {
                DetailsCard {
                    Column(Modifier.fillMaxWidth().padding(Padding.Medium), horizontalAlignment = Alignment.CenterHorizontally) {
                        RatingBar(value = ratingState.value, numStars = 10, stepSize = StepSize.HALF,
                            ratingBarStyle = RatingBarStyle.HighLighted, onValueChange = { ratingState.value = it }) {
                            viewModel.rating.value = it
                            viewModel.addWatched(it)
                        }
                    }
                }
            }
        }
        if (videosState.videos.isNotEmpty()) {
            Column(Modifier.padding(horizontal = Padding.Default)) {
                Spacer(modifier = Modifier.height(Padding.Medium))
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
        }
        showState.show?.let { show ->
            Column(Modifier.padding(horizontal = Padding.Default)) {
                if (!show.overview.isNullOrEmpty()) {
                    Spacer(modifier = Modifier.height(Padding.Medium))
                    DetailTitle(text = R.string.text_description)
                    DetailsCard { ExpandableText(text = show.overview.orEmpty()) }
                }
                Spacer(modifier = Modifier.height(Padding.Medium))
                DetailTitle(text = R.string.text_details)
                DetailsCard {
                    Column(modifier = Modifier.padding(Padding.Medium)) {
                        DetailText(name = R.string.text_original_title, detail = ": ${show.originalName}")
                        DetailText(name = R.string.text_status, detail = ": ${show.status}")
                        val genres = show.genres?.map { it.name }?.toFormattedString()
                        DetailText(name = R.string.text_genres, detail = ": $genres")
                    }
                }
            }
            Spacer(modifier = Modifier.height(Padding.Medium))
            Seasons()
            Column(Modifier.padding(horizontal = Padding.Default)) {
                Spacer(modifier = Modifier.height(Padding.Medium))
                Cast(navController)
                Spacer(modifier = Modifier.height(Padding.Medium))
                Crew(navController)
                Spacer(modifier = Modifier.height(Padding.Medium))
                SimilarMovies(navController)
                Spacer(modifier = Modifier.height(Padding.Medium))
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Seasons(
    viewModel: ShowViewModel = hiltViewModel()
) {
    val show = viewModel.showState.value.show

    if (show?.seasons?.isNotEmpty() == true) {
        Column(Modifier.padding(horizontal = Padding.Default)) {
            DetailTitle(text = R.string.text_seasons)
        }
        LazyRow(
            contentPadding = PaddingValues(horizontal = Padding.Default),
            horizontalArrangement = Arrangement.spacedBy(Padding.Small)
        ) {
            show.seasons?.let { seasons ->
                items(seasons.size) {
                    val season = seasons[it]
                    SeasonCard(season)
                }
            }
        }

    }
}

@Composable
fun Cast(
    navController: NavController,
    viewModel: ShowViewModel = hiltViewModel()
) {
    val creditState = viewModel.creditsState.value

    if (creditState.credits?.cast?.isNotEmpty() == true) {
        DetailTitle(text = R.string.text_cast)
        DetailsCard {
            LazyRow(Modifier.padding(Padding.Medium)) {
                creditState.credits.cast.let { cast ->
                    items(cast.size) {
                        val actor = cast[it]
                        Avatar(imageUrl = actor.profilePath, title = actor.name, secondary = actor.character) {
                            navController.navigate(Screen.PersonScreen.route + "/${actor.id}")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Crew(
    navController: NavController,
    viewModel: ShowViewModel = hiltViewModel()
) {
    val creditState = viewModel.creditsState.value

    if (creditState.credits?.crew?.isNotEmpty() == true) {
        DetailTitle(text = R.string.text_crew)
        DetailsCard {
            LazyRow(Modifier.padding(Padding.Medium)) {
                creditState.credits.crew.let { crew ->
                    items(crew.size) {
                        val actor = crew[it]
                        Avatar(imageUrl = actor.profilePath, title = actor.name, secondary = actor.knownForDepartment) {
                            navController.navigate(Screen.PersonScreen.route + "/${actor.id}")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SimilarMovies(
    navController: NavController,
    viewModel: ShowViewModel = hiltViewModel()
) {
    val similarState = viewModel.similarState.value
    val page = viewModel.similarShowsPage.value

    if (similarState.shows.isNotEmpty()) {
        DetailTitle(text = R.string.text_similar)
        DetailsCard {
            LazyRow(
                modifier = Modifier.padding(Padding.Medium),
                horizontalArrangement = Arrangement.spacedBy(Padding.Small)
            ) {
                itemsIndexed(items = similarState.shows) { index, show ->
                    viewModel.onChangePosition(index)
                    if ((index + 1) >= (page * Constants.PAGE_SIZE)) {
                        viewModel.getMoreSimilarShows()
                    }
                    ImageCard(imageUrl = show.posterPath, title = show.title) {
                        navController.navigate(Screen.TvShowScreen.route + "/${show.id}")
                    }
                }
            }
        }
    }
}