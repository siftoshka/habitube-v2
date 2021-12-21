package az.siftoshka.habitube.presentation.components.image

import android.content.Context
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
import az.siftoshka.habitube.presentation.util.Padding
import coil.compose.rememberImagePainter
import coil.request.CachePolicy
import java.io.File

@Composable
fun LibraryMinimalCard(
    context: Context,
    imageUrl: String,
    isWatched: Boolean,
) {
    val imagePath = imageUrl.replace("/", "-")
    val configText = if (isWatched) "/watched" else "/planned"

    Card(
        modifier = Modifier
            .width(90.dp)
            .height(135.dp)
            .padding(Padding.ExtraSmall),
        shape = MaterialTheme.shapes.large,
        elevation = 4.dp,
    ) {
        Image(
            painter = rememberImagePainter(
                data = File(context.filesDir.path + configText + imagePath),
                builder = {
                    crossfade(true)
                    error(R.drawable.ic_placeholder)
                    memoryCachePolicy(CachePolicy.ENABLED)
                    diskCachePolicy(CachePolicy.DISABLED)
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