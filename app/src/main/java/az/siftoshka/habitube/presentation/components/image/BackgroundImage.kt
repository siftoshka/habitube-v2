package az.siftoshka.habitube.presentation.components.image

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import az.siftoshka.habitube.R
import az.siftoshka.habitube.domain.util.Constants.IMAGE_URL
import az.siftoshka.habitube.presentation.util.Padding
import coil.compose.rememberImagePainter
import coil.request.CachePolicy

@Composable
fun BackgroundImage(
    modifier: Modifier,
    imageUrl: String?,
    onItemClick: () -> Unit
) {
    Card(
        modifier = modifier,
        elevation = 4.dp
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
                contentDescription = imageUrl,
                contentScale = ContentScale.FillWidth,
                modifier = Modifier.fillMaxSize()
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(Color.Black, Color.Transparent, Color.Black),
                            startY = -100f,
                            endY = 800f
                        )
                    )
            )
            Box(modifier = Modifier.fillMaxSize().padding(Padding.Default)) {
                IconButton(
                    onClick = { onItemClick() },
                    modifier = Modifier.size(30.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_back),
                        tint = Color.White,
                        contentDescription = imageUrl,
                    )
                }
            }
        }
    }
}