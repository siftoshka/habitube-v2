package az.siftoshka.habitube.presentation.screens.show.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import az.siftoshka.habitube.R
import az.siftoshka.habitube.domain.model.Season
import az.siftoshka.habitube.domain.util.Constants
import az.siftoshka.habitube.domain.util.onlyYear
import az.siftoshka.habitube.presentation.theme.spacing
import coil.compose.rememberImagePainter
import coil.request.CachePolicy

@Composable
fun SeasonCard(season: Season?) {
    Card(
        elevation = 4.dp,
        shape = MaterialTheme.shapes.large,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(MaterialTheme.spacing.small)) {
            ImageCard(season)
            Spacer(modifier = Modifier.height(MaterialTheme.spacing.small))
            Text(
                text = buildAnnotatedString {
                    append("${season?.name}")
                    append(
                        AnnotatedString(
                            " (${season?.airDate?.onlyYear()})",
                            spanStyle = SpanStyle(color = MaterialTheme.colors.secondaryVariant, fontWeight = FontWeight.Light)
                        )
                    )
                },
                style = MaterialTheme.typography.h4,
                color = MaterialTheme.colors.onBackground,
                textAlign = TextAlign.Start,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(MaterialTheme.spacing.extraSmall))
            Text(
                text = buildAnnotatedString {
                    append(stringResource(id = R.string.text_episodes))
                    append(
                        AnnotatedString(
                            ": ${season?.episodeCount}",
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
fun ImageCard(season: Season?) {
    Card(
        modifier = Modifier.size(160.dp),
        shape = MaterialTheme.shapes.large,
        elevation = 4.dp,
    ) {
        Image(
            painter = rememberImagePainter(
                data = Constants.IMAGE_URL + season?.posterPath,
                builder = {
                    crossfade(true)
                    error(R.drawable.ic_placeholder)
                    memoryCachePolicy(CachePolicy.ENABLED)
                    diskCachePolicy(CachePolicy.DISABLED)
                    networkCachePolicy(CachePolicy.ENABLED)
                }
            ),
            contentDescription = season?.name,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxWidth()
        )
    }
}