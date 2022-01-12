package az.siftoshka.habitube.presentation.screens.library.sections

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import az.siftoshka.habitube.R
import az.siftoshka.habitube.domain.model.TvShow
import az.siftoshka.habitube.domain.util.isInternetAvailable
import az.siftoshka.habitube.domain.util.onlyYear
import az.siftoshka.habitube.presentation.screens.library.components.LibraryMinimalCard
import az.siftoshka.habitube.presentation.theme.spacing

@Composable
fun InfoShowSection(show: TvShow?, isWatched: Boolean) {

    val context = LocalContext.current

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = MaterialTheme.spacing.default, vertical = MaterialTheme.spacing.small)
    ) {
        if (context.isInternetAvailable()) LibraryMinimalCard(imageUrl = show?.posterPath.orEmpty())
        Column(modifier = Modifier.padding(horizontal = MaterialTheme.spacing.small)) {
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
            Spacer(modifier = Modifier.height(MaterialTheme.spacing.extraSmall))
            if (isWatched) {
                Text(
                    text = buildAnnotatedString {
                        append(stringResource(id = R.string.text_my_rating))
                        append(
                            AnnotatedString(
                                ": ${show?.myRating}",
                                spanStyle = SpanStyle(color = MaterialTheme.colors.secondaryVariant, fontWeight = FontWeight.Normal)
                            )
                        )
                    },
                    style = MaterialTheme.typography.h4,
                    color = MaterialTheme.colors.onBackground,
                    textAlign = TextAlign.Start,
                )
            }
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
                style = MaterialTheme.typography.h4,
                color = MaterialTheme.colors.onBackground,
                textAlign = TextAlign.Start,
            )
            Text(
                text = buildAnnotatedString {
                    append(stringResource(id = R.string.text_episodes))
                    append(
                        AnnotatedString(
                            ": ${show?.numberOfEpisodes}",
                            spanStyle = SpanStyle(color = MaterialTheme.colors.secondaryVariant, fontWeight = FontWeight.Normal)
                        )
                    )
                },
                style = MaterialTheme.typography.h4,
                color = MaterialTheme.colors.onBackground,
                textAlign = TextAlign.Start,
            )
        }
    }
}