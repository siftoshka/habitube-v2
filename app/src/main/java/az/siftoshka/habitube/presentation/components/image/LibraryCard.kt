package az.siftoshka.habitube.presentation.components.image

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layout
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import az.siftoshka.habitube.R
import az.siftoshka.habitube.presentation.util.Padding
import coil.compose.rememberImagePainter
import coil.request.CachePolicy
import java.io.File

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun LibraryCard(
    context: Context,
    title: String,
    imageUrl: String,
    rating: Float? = 0f,
    isWatched: Boolean,
    onItemClick: () -> Unit
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
        onClick = onItemClick
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
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
                contentDescription = title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .width(90.dp)
                    .height(135.dp)
            )
            if (rating?.let { it.compareTo(0f) > 0 } == true) {
                Box(modifier = Modifier.fillMaxSize()) {
                    Row(
                        horizontalArrangement = Arrangement.End, modifier = Modifier
                            .fillMaxWidth()
                            .padding(2.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .background(MaterialTheme.colors.background.copy(alpha = 0.5f), shape = CircleShape)
                                .layout { measurable, constraints ->
                                    val placeable = measurable.measure(constraints)
                                    val currentHeight = placeable.height
                                    var heightCircle = currentHeight
                                    if (placeable.width > heightCircle) heightCircle = placeable.width
                                    layout(heightCircle, heightCircle) {
                                        placeable.placeRelative(0, (heightCircle - currentHeight) / 2)
                                    }
                                }) {

                            Text(
                                text = rating.toString(),
                                style = MaterialTheme.typography.h6,
                                color = MaterialTheme.colors.onBackground,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.defaultMinSize(20.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}