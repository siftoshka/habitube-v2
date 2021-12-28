package az.siftoshka.habitube.presentation.screens.library.sections

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import az.siftoshka.habitube.R
import az.siftoshka.habitube.domain.model.TvShow
import az.siftoshka.habitube.domain.util.onlyYear
import az.siftoshka.habitube.presentation.components.image.LibraryMinimalCard
import az.siftoshka.habitube.presentation.util.Padding

@Composable
fun InfoShowSection(show: TvShow?, isWatched: Boolean) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = Padding.Default, vertical = Padding.Small)
    ) {
        LibraryMinimalCard(imageUrl = show?.posterPath.orEmpty())
        Column(
            modifier = Modifier
                .padding(horizontal = Padding.Small)
                .height(150.dp)
        ) {
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