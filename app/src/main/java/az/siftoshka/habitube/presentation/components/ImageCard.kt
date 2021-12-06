package az.siftoshka.habitube.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import az.siftoshka.habitube.domain.util.Constants.IMAGE_URL
import com.skydoves.landscapist.coil.CoilImage

@Composable
fun ImageCard(
    imageUrl: String?,
    title: String?
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.large,
        elevation = 4.dp
    ) {
        Box(modifier = Modifier.width(100.dp)) {
            CoilImage(
                imageModel = IMAGE_URL + imageUrl,
                contentDescription = title,
                contentScale = ContentScale.Crop
            )
        }
    }
}