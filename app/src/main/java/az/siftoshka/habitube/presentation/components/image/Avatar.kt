package az.siftoshka.habitube.presentation.components.image

import androidx.compose.foundation.Image
import androidx.compose.foundation.Indication
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import az.siftoshka.habitube.R
import az.siftoshka.habitube.domain.util.Constants.IMAGE_URL
import az.siftoshka.habitube.presentation.theme.spacing
import coil.compose.rememberImagePainter
import coil.request.CachePolicy

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun Avatar(
    imageUrl: String?,
    title: String?,
    secondary: String?,
    indication: Indication? = rememberRipple(),
    onItemClick: () -> Unit
) {
    Column(modifier = Modifier.width(100.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Card(
            modifier = Modifier.size(80.dp),
            shape = RoundedCornerShape(100.dp),
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
                modifier = Modifier.fillMaxWidth()
            )
        }
        Text(
            text = title.orEmpty(),
            style = MaterialTheme.typography.h5,
            color = MaterialTheme.colors.onSurface,
            textAlign = TextAlign.Center,
            maxLines = 1,
            overflow = TextOverflow.Clip,
            modifier = Modifier.padding(top = MaterialTheme.spacing.ultraSmall)
        )
        Text(
            text = secondary.orEmpty(),
            style = MaterialTheme.typography.h6,
            color = MaterialTheme.colors.secondaryVariant,
            textAlign = TextAlign.Center,
            maxLines = 1,
            overflow = TextOverflow.Clip
        )
    }
}