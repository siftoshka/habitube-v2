package az.siftoshka.habitube.presentation.screens.movie

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import az.siftoshka.habitube.R
import az.siftoshka.habitube.domain.util.onlyYear
import az.siftoshka.habitube.presentation.components.BackgroundImage
import az.siftoshka.habitube.presentation.components.ImageCard
import az.siftoshka.habitube.presentation.theme.HabitubeV2Theme
import az.siftoshka.habitube.presentation.util.Padding
import com.google.accompanist.systemuicontroller.rememberSystemUiController

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
    var selectedTabIndex by remember { mutableStateOf(MovieTabs.INFO.value) }

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
                Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                    TabView(
                        tabs = listOf(R.string.tab_info, R.string.tab_cast, R.string.tab_crew, R.string.tab_similar)
                    ) {
                        selectedTabIndex = it
                    }
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
    val movie = viewModel.movieState.value.movie

    BackgroundImage(imageUrl = movie?.backdropPath) { navController.popBackStack() }
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

@Composable
fun TabView(
    tabs: List<Int>,
    onTabSelected: (selectedIndex: Int) -> Unit
) {
    var selectedTabIndex by remember {
        mutableStateOf(MovieTabs.INFO.value)
    }
    val inactiveColor = MaterialTheme.colors.onBackground
    TabRow(
        backgroundColor = Color.Transparent,
        contentColor = MaterialTheme.colors.primary,
        selectedTabIndex = selectedTabIndex
    ) {
        tabs.forEachIndexed { index, title ->
            Tab(
                selected = selectedTabIndex == index,
                selectedContentColor = MaterialTheme.colors.primary,
                unselectedContentColor = inactiveColor,
                onClick = {
                    selectedTabIndex = index
                    onTabSelected(index)
                }
            ) {
                Text(
                    text = stringResource(id = title),
                    style = MaterialTheme.typography.body1,
                    color = if (selectedTabIndex == index) MaterialTheme.colors.primary else inactiveColor,
                    modifier = Modifier.padding(vertical = Padding.Medium)
                )
            }
        }
    }
}