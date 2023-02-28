package az.siftoshka.habitube.presentation.screens.library.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import az.siftoshka.habitube.R
import az.siftoshka.habitube.domain.util.Constants.IMAGE_URL
import az.siftoshka.habitube.presentation.theme.spacing
import coil.compose.rememberAsyncImagePainter
import coil.request.CachePolicy
import coil.request.ImageRequest
import coil.size.Precision

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun LibraryCard(
    title: String,
    imageUrl: String,
    rating: Float? = 0f,
    onItemClick: () -> Unit
) {

    Card(
        modifier = Modifier
            .width(90.dp)
            .height(135.dp)
            .padding(MaterialTheme.spacing.extraSmall),
        shape = MaterialTheme.shapes.large,
        elevation = 4.dp,
        onClick = onItemClick
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = rememberAsyncImagePainter(
                    ImageRequest.Builder(LocalContext.current).data(data = IMAGE_URL + imageUrl)
                        .apply(block = fun ImageRequest.Builder.() {
                            crossfade(true)
                            crossfade(300)
                            precision(Precision.INEXACT)
                            error(R.drawable.ic_placeholder)
                            memoryCacheKey(IMAGE_URL + imageUrl)
                            memoryCachePolicy(CachePolicy.ENABLED)
                            networkCachePolicy(CachePolicy.ENABLED)
                        }).build()
                ),
                contentDescription = title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .width(90.dp)
                    .height(135.dp)
            )
            if (rating?.let { it.compareTo(0f) > 0 } == true) {
                Box(modifier = Modifier.fillMaxSize()) {
                    Row(horizontalArrangement = Arrangement.End, modifier = Modifier.fillMaxWidth()) {
                        Text(
                            text = "$rating",
                            style = MaterialTheme.typography.h6,
                            color = Color.White,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .background(Color.Black.copy(alpha = 0.7f), RoundedCornerShape(bottomStart = 12.dp))
                                .padding(start = 4.dp, end = 2.dp)
                                .padding(vertical = 2.dp)
                        )
                    }
                }
            }
        }
    }
}