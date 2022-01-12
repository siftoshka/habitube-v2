package az.siftoshka.habitube.presentation.screens.library.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import az.siftoshka.habitube.R
import az.siftoshka.habitube.domain.util.Constants
import az.siftoshka.habitube.presentation.theme.spacing
import coil.compose.rememberImagePainter
import coil.request.CachePolicy
import coil.size.Precision

@Composable
fun LibraryMinimalCard(imageUrl: String) {
    Card(
        modifier = Modifier
            .width(90.dp)
            .height(135.dp)
            .padding(MaterialTheme.spacing.extraSmall),
        shape = MaterialTheme.shapes.large,
        elevation = 4.dp,
    ) {
        Image(
            painter = rememberImagePainter(
                data = Constants.IMAGE_URL + imageUrl,
                builder = {
                    crossfade(true)
                    precision(Precision.INEXACT)
                    error(R.drawable.ic_placeholder)
                    memoryCacheKey(Constants.IMAGE_URL + imageUrl)
                    memoryCachePolicy(CachePolicy.READ_ONLY)
                    networkCachePolicy(CachePolicy.ENABLED)
                }
            ),
            contentDescription = imageUrl,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .width(90.dp)
                .height(135.dp)
        )
    }
}