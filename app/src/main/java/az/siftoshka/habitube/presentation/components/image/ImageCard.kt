package az.siftoshka.habitube.presentation.components.image

import androidx.compose.foundation.Image
import androidx.compose.foundation.Indication
import androidx.compose.foundation.background
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import az.siftoshka.habitube.R
import az.siftoshka.habitube.domain.util.Constants.IMAGE_URL
import az.siftoshka.habitube.presentation.theme.spacing
import coil.compose.rememberImagePainter
import coil.request.CachePolicy

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ImageCard(
    imageUrl: String?,
    title: String?,
    rating: Double? = 0.0,
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
        Box(modifier = Modifier.fillMaxSize()) {
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
            if (rating?.let { it.compareTo(0f) > 0 } == true) {
                Box(modifier = Modifier.fillMaxSize()) {
                    Row(
                        horizontalArrangement = Arrangement.End, modifier = Modifier
                            .fillMaxWidth()
                            .padding(MaterialTheme.spacing.extraSmall)
                    ) {
                        Text(
                            text = " $rating ",
                            style = MaterialTheme.typography.h6,
                            color = Color.White,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .background(Color.Black.copy(alpha = 0.5f), RoundedCornerShape(100.dp))
                        )
                    }
                    Row(
                        verticalAlignment = Alignment.Bottom,
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Text(
                            text = stringResource(id = R.string.text_watched),
                            style = MaterialTheme.typography.h6,
                            color = Color.White,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .background(MaterialTheme.colors.primary.copy(alpha = 0.6f), MaterialTheme.shapes.small)
                                .fillMaxWidth()
                        )
                    }
                }
            }
        }
    }
}