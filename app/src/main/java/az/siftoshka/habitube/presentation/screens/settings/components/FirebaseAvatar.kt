package az.siftoshka.habitube.presentation.screens.settings.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import az.siftoshka.habitube.R
import coil.compose.rememberAsyncImagePainter
import coil.request.CachePolicy
import coil.request.ImageRequest

@Composable
fun FirebaseAvatar(
    imageUrl: String?,
    title: String?,
) {
    Column(modifier = Modifier.width(80.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Card(
            modifier = Modifier.size(80.dp),
            shape = RoundedCornerShape(100.dp),
            elevation = 4.dp
        ) {
            Image(
                painter = rememberAsyncImagePainter(
                    ImageRequest.Builder(LocalContext.current).data(data = imageUrl)
                        .apply(block = fun ImageRequest.Builder.() {
                            crossfade(true)
                            error(R.drawable.ic_placeholder)
                            memoryCachePolicy(CachePolicy.ENABLED)
                            diskCachePolicy(CachePolicy.ENABLED)
                            networkCachePolicy(CachePolicy.ENABLED)
                        }).build()
                ),
                contentDescription = title,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}