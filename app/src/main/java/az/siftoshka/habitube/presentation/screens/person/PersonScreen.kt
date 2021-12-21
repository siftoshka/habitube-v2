package az.siftoshka.habitube.presentation.screens.person

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
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
import az.siftoshka.habitube.domain.util.normalDate
import az.siftoshka.habitube.presentation.components.DetailsCard
import az.siftoshka.habitube.presentation.components.image.ImageCard
import az.siftoshka.habitube.presentation.components.image.PersonCard
import az.siftoshka.habitube.presentation.components.screen.LoadingScreen
import az.siftoshka.habitube.presentation.components.text.DetailTitle
import az.siftoshka.habitube.presentation.components.text.ExpandableText
import az.siftoshka.habitube.presentation.theme.HabitubeV2Theme
import az.siftoshka.habitube.presentation.util.Padding
import az.siftoshka.habitube.presentation.util.Screen
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import java.lang.Float.min

/**
 * Composable function of the Person Screen.
 */
@Composable
fun PersonScreen(
    navController: NavController,
    viewModel: PersonViewModel = hiltViewModel()
) {
    val systemUiController = rememberSystemUiController()
    systemUiController.setSystemBarsColor(color = MaterialTheme.colors.background)
    val scrollState = rememberScrollState()

    val personState = viewModel.personState.value

    HabitubeV2Theme {
        Surface(color = MaterialTheme.colors.background, modifier = Modifier.fillMaxSize()) {
            Column(modifier = Modifier.fillMaxSize()) {
                if (personState.isLoading) { LoadingScreen() }
                MainBoard(scrollState, navController)
            }
        }
    }
}

@Composable
fun MainBoard(
    scrollState: ScrollState,
    navController: NavController,
    viewModel: PersonViewModel = hiltViewModel()
) {
    val person = viewModel.personState.value.person
    val context = LocalContext.current

    Column(Modifier.verticalScroll(scrollState)) {
        PersonCard(
            modifier = Modifier
                .fillMaxWidth()
                .height((300 - (scrollState.value * 0.15f)).dp)
                .graphicsLayer {
                    alpha = min(1f, 1 - (scrollState.value / 600f))
                    translationY = -scrollState.value * 0.1f
                },
            imageUrl = person?.profilePath
        ) { navController.popBackStack() }

        Column(modifier = Modifier.padding(horizontal = Padding.Default, vertical = Padding.Small)) {
            Text(
                text = person?.name.orEmpty(),
                style = MaterialTheme.typography.h1,
                color = MaterialTheme.colors.onBackground,
                textAlign = TextAlign.Start,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = buildAnnotatedString {
                    append(person?.birthday.normalDate(context))
                    if (!person?.deathday.isNullOrEmpty()) append(" - ${person?.deathday.normalDate(context)}")
                },
                style = MaterialTheme.typography.h5,
                color = MaterialTheme.colors.onBackground,
                textAlign = TextAlign.Start,
            )
            Spacer(modifier = Modifier.height(Padding.ExtraSmall))
            Text(
                text = buildAnnotatedString {
                    append(stringResource(id = R.string.text_known_for))
                    append(
                        AnnotatedString(
                            ": ${person?.knownForDepartment ?: "-"}",
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
                    append(stringResource(id = R.string.text_birth_place))
                    append(
                        AnnotatedString(
                            ": ${person?.placeOfBirth ?: "-"}",
                            spanStyle = SpanStyle(color = MaterialTheme.colors.secondaryVariant, fontWeight = FontWeight.Normal)
                        )
                    )
                },
                style = MaterialTheme.typography.h5,
                color = MaterialTheme.colors.onBackground,
                textAlign = TextAlign.Start,
            )
            person?.let { person ->
                if (!person.biography.isNullOrEmpty()) {
                    Spacer(modifier = Modifier.height(Padding.Medium))
                    DetailTitle(text = R.string.text_biography)
                    DetailsCard { ExpandableText(text = person.biography.orEmpty()) }
                }
                Spacer(modifier = Modifier.height(Padding.Medium))
                MovieCast(navController)
                Spacer(modifier = Modifier.height(Padding.Medium))
                MovieCrew(navController)
                Spacer(modifier = Modifier.height(Padding.Medium))
                ShowCast(navController)
                Spacer(modifier = Modifier.height(Padding.Medium))
                ShowCrew(navController)
                Spacer(modifier = Modifier.height(Padding.Medium))
            }
        }
    }
}

@Composable
fun MovieCast(
    navController: NavController,
    viewModel: PersonViewModel = hiltViewModel()
) {
    val creditState = viewModel.personMoviesState.value

    if (creditState.credits?.cast?.isNotEmpty() == true) {
        DetailTitle(text = R.string.text_person_acting, secondary = R.string.text_movies)
        DetailsCard {
            LazyRow(
                modifier = Modifier.padding(Padding.Medium),
                horizontalArrangement = Arrangement.spacedBy(Padding.Small)
            ) {
                creditState.credits.cast.let { movies ->
                    items(movies.size) {
                        val movie = movies[it]
                        ImageCard(imageUrl = movie.posterPath, title = movie.name) {
                            navController.navigate(Screen.MovieScreen.route + "/${movie.id}")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun MovieCrew(
    navController: NavController,
    viewModel: PersonViewModel = hiltViewModel()
) {
    val creditState = viewModel.personMoviesState.value

    if (creditState.credits?.crew?.isNotEmpty() == true) {
        DetailTitle(text = R.string.text_person_crew, secondary = R.string.text_movies)
        DetailsCard {
            LazyRow(
                modifier = Modifier.padding(Padding.Medium),
                horizontalArrangement = Arrangement.spacedBy(Padding.Small)
            ) {
                creditState.credits.crew.let { movies ->
                    items(movies.size) {
                        val movie = movies[it]
                        ImageCard(imageUrl = movie.posterPath, title = movie.name) {
                            navController.navigate(Screen.MovieScreen.route + "/${movie.id}")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ShowCast(
    navController: NavController,
    viewModel: PersonViewModel = hiltViewModel()
) {
    val creditState = viewModel.personShowsState.value

    if (creditState.credits?.cast?.isNotEmpty() == true) {
        DetailTitle(text = R.string.text_person_acting, secondary = R.string.text_shows)
        DetailsCard {
            LazyRow(
                modifier = Modifier.padding(Padding.Medium),
                horizontalArrangement = Arrangement.spacedBy(Padding.Small)
            ) {
                creditState.credits.cast.let { shows ->
                    items(shows.size) {
                        val show = shows[it]
                        ImageCard(imageUrl = show.posterPath, title = show.name) {
                            navController.navigate(Screen.TvShowScreen.route + "/${show.id}")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ShowCrew(
    navController: NavController,
    viewModel: PersonViewModel = hiltViewModel()
) {
    val creditState = viewModel.personShowsState.value

    if (creditState.credits?.crew?.isNotEmpty() == true) {
        DetailTitle(text = R.string.text_person_crew, secondary = R.string.text_shows)
        DetailsCard {
            LazyRow(
                modifier = Modifier.padding(Padding.Medium),
                horizontalArrangement = Arrangement.spacedBy(Padding.Small)
            ) {
                creditState.credits.crew.let { shows ->
                    items(shows.size) {
                        val show = shows[it]
                        ImageCard(imageUrl = show.posterPath, title = show.name) {
                            navController.navigate(Screen.TvShowScreen.route + "/${show.id}")
                        }
                    }
                }
            }
        }
    }
}