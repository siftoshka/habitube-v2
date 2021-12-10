package az.siftoshka.habitube.presentation.screens.movie

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import az.siftoshka.habitube.presentation.components.*
import az.siftoshka.habitube.presentation.theme.HabitubeV2Theme
import az.siftoshka.habitube.presentation.util.Padding
import com.google.accompanist.flowlayout.FlowRow
import com.google.accompanist.pager.*
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.launch

/**
 * Composable function of the Movie Screen.
 */
@OptIn(ExperimentalPagerApi::class)
@Composable
fun MovieScreen(
    navController: NavController,
    viewModel: MovieViewModel = hiltViewModel()
) {
    val systemUiController = rememberSystemUiController()
    systemUiController.setSystemBarsColor(color = MaterialTheme.colors.background)
    val pagerState = rememberPagerState()
    val scope = rememberCoroutineScope()

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
                MainBoard(navController)
                TabView(pagerState) {
                    scope.launch { pagerState.animateScrollToPage(it) }
                }
            }
        }
    }
}

@Composable
fun MainBoard(
    navController: NavController,
    viewModel: MovieViewModel = hiltViewModel()
) {
    val offset = viewModel.imageOffset.observeAsState()
    val movie = viewModel.movieState.value.movie
    Column {
        BackgroundImage(
            modifier = Modifier
                .fillMaxWidth()
                .height(offset.value?.dp ?: 200.dp),
            imageUrl = movie?.backdropPath
        ) { navController.popBackStack() }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = Padding.Default)
                .padding(top = Padding.Small)
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

@OptIn(ExperimentalPagerApi::class)
@Composable
fun TabView(
    pagerState: PagerState,
    viewModel: MovieViewModel = hiltViewModel(),
    onTabSelected: (selectedIndex: Int) -> Unit
) {
    val tabs = listOf(R.string.tab_info, R.string.tab_cast, R.string.tab_crew, R.string.tab_similar)
    val inactiveColor = MaterialTheme.colors.onBackground

    TabRow(
        indicator = { tabPositions -> TabRowDefaults.Indicator(Modifier.pagerTabIndicatorOffset(pagerState, tabPositions)) },
        backgroundColor = Color.Transparent,
        contentColor = MaterialTheme.colors.primary,
        selectedTabIndex = pagerState.currentPage
    ) {
        tabs.forEachIndexed { index, title ->
            Tab(
                selected = pagerState.currentPage == index,
                selectedContentColor = MaterialTheme.colors.primary,
                unselectedContentColor = inactiveColor,
                onClick = { onTabSelected(index) },
                text = {
                    Text(
                        text = stringResource(id = title),
                        style = MaterialTheme.typography.body1,
                        color = if (pagerState.currentPage == index) MaterialTheme.colors.primary else inactiveColor
                    )
                }
            )
        }
    }
    HorizontalPager(count = tabs.size, state = pagerState) { page ->
        when (page) {
            MovieTabs.INFO.value -> InfoTab()
            MovieTabs.CAST.value -> CastTab()
            MovieTabs.CREW.value -> CrewTab()
            MovieTabs.SIMILAR.value -> SimilarTab()
        }
        viewModel.updateOffset(0)
    }
}

@Composable
fun InfoTab(
    viewModel: MovieViewModel = hiltViewModel()
) {
    val scrollState = rememberScrollState()
    viewModel.updateOffset(scrollState.value)
    val movieState = viewModel.movieState.value
    val videosState = viewModel.videosState.value
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(Padding.Medium)
    ) {
        if (videosState.videos.isNotEmpty()) {
            TitleText(text = R.string.text_videos)
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
            TitleText(text = R.string.text_description)
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
            TitleText(text = R.string.text_details)
            DetailsCard {
                Column(modifier = Modifier.padding(Padding.Medium)) {
                    DetailText(name = R.string.text_budget, detail = ": $${movie.budget.toString().moneyFormat()}")
                    DetailText(name = R.string.text_revenue, detail = ": $${movie.revenue.toString().moneyFormat()}")
                    val languages = movie.spokenLanguages?.map { it.englishName }?.toFormattedString()
                    DetailText(name = R.string.text_spoken_languages, detail = ": $languages")
                    val companies = movie.productionCompanies?.map { it.name }?.toFormattedString()
                    DetailText(name = R.string.text_companies, detail = ": $companies")
                }
            }
            Spacer(modifier = Modifier.height(Padding.Medium))
            TitleText(text = R.string.text_genres)
            DetailsCard {
                FlowRow(
                    mainAxisSpacing = Padding.Small,
                    crossAxisSpacing = Padding.Small,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(Padding.Medium)
                ) {
                    movie.genres?.forEach { movieGenre ->
                        Genre(genre = movieGenre.name.orEmpty())
                    }
                }
            }
            Spacer(modifier = Modifier.height(128.dp))
        }
    }
}

@Composable
fun CastTab() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(Padding.Default)
    ) {
        Text(
            text = stringResource(id = R.string.text_runtime),
            style = MaterialTheme.typography.h2,
            color = MaterialTheme.colors.onBackground,
            textAlign = TextAlign.Start,
        )
    }
}

@Composable
fun CrewTab() {

}

@Composable
fun SimilarTab() {

}