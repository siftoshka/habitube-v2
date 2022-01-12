package az.siftoshka.habitube.presentation.screens.movie.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import az.siftoshka.habitube.R
import az.siftoshka.habitube.presentation.components.DetailsCard
import az.siftoshka.habitube.presentation.screens.movie.MovieViewModel
import az.siftoshka.habitube.presentation.theme.spacing

@Composable
fun RatingsBoard(viewModel: MovieViewModel = hiltViewModel()) {

    val movie = viewModel.movieState.value.movie
    val omdbData = viewModel.omdbState.value.data

    val movieDbRating = "${movie?.voteAverage} (${movie?.voteCount})"
    val imdbRating = "${omdbData?.imdbRating} (${omdbData?.imdbVotes})"
    val ratings = omdbData?.ratings ?: emptyList()

    var tomatoValue: String? = null
    var metacriticValue: String? = null

    ratings.forEach {
        when (it.source) {
            "Rotten Tomatoes" -> tomatoValue = it.value
            "Metacritic" -> metacriticValue = it.value
        }
    }
    DetailsCard {
        Row(
            horizontalArrangement = Arrangement.SpaceAround,
            modifier = Modifier
                .fillMaxWidth()
                .padding(MaterialTheme.spacing.medium)
        ) {
            omdbData?.imdbRating?.let { RatingCard(value = imdbRating, icon = R.drawable.ic_imdb_logo) }
            RatingCard(value = metacriticValue, icon = R.drawable.ic_metascore_logo)
            RatingCard(value = tomatoValue, icon = R.drawable.ic_rotten_tomatoes_logo)
            movie?.voteAverage?.let { RatingCard(value = movieDbRating, icon = R.drawable.ic_moviedb_logo) }
        }
    }
}

@Composable
fun RatingCard(value: String?, @DrawableRes icon: Int) {
    if (value != null) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                painter = painterResource(id = icon),
                contentDescription = value,
                modifier = Modifier.size(36.dp)
            )
            Text(
                text = value.orEmpty(),
                style = MaterialTheme.typography.h5,
                color = MaterialTheme.colors.onSurface,
                textAlign = TextAlign.Center,
                maxLines = 1,
                modifier = Modifier.padding(top = MaterialTheme.spacing.extraSmall)
            )
        }
    }
}