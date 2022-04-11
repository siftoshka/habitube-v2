package az.siftoshka.habitube.presentation.screens.person

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
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
import az.siftoshka.habitube.domain.util.Constants
import az.siftoshka.habitube.domain.util.normalDate
import az.siftoshka.habitube.domain.util.shareLink
import az.siftoshka.habitube.presentation.components.DetailsCard
import az.siftoshka.habitube.presentation.components.image.ImageCard
import az.siftoshka.habitube.presentation.components.screen.LoadingScreen
import az.siftoshka.habitube.presentation.components.text.DetailTitle
import az.siftoshka.habitube.presentation.components.text.ExpandableText
import az.siftoshka.habitube.presentation.screens.person.components.PersonCard
import az.siftoshka.habitube.presentation.theme.spacing
import az.siftoshka.habitube.presentation.util.Screen
import java.lang.Float.min

/**
 * Composable function of the Person Screen.
 */
@Composable
fun PersonScreen(
    navController: NavController,
    viewModel: PersonViewModel = hiltViewModel()
) {
    val scrollState = rememberScrollState()
    val personState = viewModel.personState.value

    Surface(color = MaterialTheme.colors.background, modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {
            if (personState.isLoading) LoadingScreen()
            MainBoard(scrollState, navController)
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
            imageUrl = person?.profilePath,
            onBack = { navController.popBackStack() },
            onShare = { context.shareLink(person?.homepage ?: Constants.PERSON_THEMOVIEDB + person?.id) }
        )

        Column(modifier = Modifier.padding(horizontal = MaterialTheme.spacing.default, vertical = MaterialTheme.spacing.small)) {
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
            Spacer(modifier = Modifier.height(MaterialTheme.spacing.extraSmall))
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
                    Spacer(modifier = Modifier.height(MaterialTheme.spacing.medium))
                    DetailTitle(text = R.string.text_biography)
                    DetailsCard { ExpandableText(text = person.biography.orEmpty()) }
                }
                Spacer(modifier = Modifier.height(MaterialTheme.spacing.medium))
                MovieCast(navController)
                Spacer(modifier = Modifier.height(MaterialTheme.spacing.medium))
                MovieCrew(navController)
                Spacer(modifier = Modifier.height(MaterialTheme.spacing.medium))
                ShowCast(navController)
                Spacer(modifier = Modifier.height(MaterialTheme.spacing.medium))
                ShowCrew(navController)
                Spacer(modifier = Modifier.height(MaterialTheme.spacing.medium))
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
                modifier = Modifier.padding(MaterialTheme.spacing.medium),
                horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small)
            ) {
                creditState.credits.cast.sortedByDescending { it.popularity }.let { movies ->
                    items(movies.size) {
                        val movie = movies[it]
                        ImageCard(imageUrl = movie.posterPath, title = movie.name, rating = movie.voteAverage) {
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
                modifier = Modifier.padding(MaterialTheme.spacing.medium),
                horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small)
            ) {
                creditState.credits.crew.sortedByDescending { it.popularity }.let { movies ->
                    items(movies.size) {
                        val movie = movies[it]
                        ImageCard(imageUrl = movie.posterPath, title = movie.name, rating = movie.voteAverage) {
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
                modifier = Modifier.padding(MaterialTheme.spacing.medium),
                horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small)
            ) {
                creditState.credits.cast.sortedByDescending { it.popularity }.let { shows ->
                    items(shows.size) {
                        val show = shows[it]
                        ImageCard(imageUrl = show.posterPath, title = show.name, rating = show.voteAverage) {
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
                modifier = Modifier.padding(MaterialTheme.spacing.medium),
                horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small)
            ) {
                creditState.credits.crew.sortedByDescending { it.popularity }.let { shows ->
                    items(shows.size) {
                        val show = shows[it]
                        ImageCard(imageUrl = show.posterPath, title = show.name, rating = show.voteAverage) {
                            navController.navigate(Screen.TvShowScreen.route + "/${show.id}")
                        }
                    }
                }
            }
        }
    }
}