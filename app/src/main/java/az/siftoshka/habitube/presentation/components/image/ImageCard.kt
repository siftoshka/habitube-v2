package az.siftoshka.habitube.presentation.components.image

import androidx.compose.foundation.Image
import androidx.compose.foundation.Indication
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import az.siftoshka.habitube.R
import az.siftoshka.habitube.domain.util.Constants.IMAGE_URL
import coil.compose.rememberImagePainter
import coil.request.CachePolicy

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ImageCard(
    imageUrl: String?,
    title: String?,
    indication: Indication? = rememberRipple(),
    onItemClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .width(100.dp)
            .height(150.dp),
        shape = MaterialTheme.shapes.large,
        elevation = 4.dp,
        indication = indication,
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
            contentDescription = title,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .width(100.dp)
                .height(150.dp)
        )
    }
}