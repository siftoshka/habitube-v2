package az.siftoshka.habitube.presentation.screens.search.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import az.siftoshka.habitube.R
import az.siftoshka.habitube.domain.model.MediaLite
import az.siftoshka.habitube.domain.util.Constants.IMAGE_URL
import az.siftoshka.habitube.presentation.theme.spacing
import coil.compose.rememberImagePainter
import coil.request.CachePolicy

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SearchCard(
    media: MediaLite,
    onItemClick: () -> Unit
) {
    val imageUrl = if (media.posterPath.isNullOrBlank()) media.profilePath else media.posterPath
    Card(
        modifier = Modifier
            .width(90.dp)
            .height(135.dp)
            .padding(MaterialTheme.spacing.extraSmall)
        ,
        shape = MaterialTheme.shapes.large,
        elevation = 4.dp,
        onClick = onItemClick
    ) {
        Image(
            painter = rememberImagePainter(
                data = IMAGE_URL + imageUrl,
                builder = {
                    crossfade(true)
                    error(R.drawable.ic_placeholder)
                    memoryCachePolicy(CachePolicy.ENABLED)
                    diskCachePolicy(CachePolicy.DISABLED)
                    networkCachePolicy(CachePolicy.ENABLED)
                }
            ),
            contentDescription = media.title,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .width(90.dp)
                .height(135.dp)
        )
    }
}