package az.siftoshka.habitube.presentation.components.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import az.siftoshka.habitube.R
import az.siftoshka.habitube.presentation.util.Padding
import com.airbnb.lottie.compose.*

@Composable
fun NoConnectionScreen() {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.no_connection))

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        LottieAnimation(
            modifier = Modifier.height(150.dp),
            composition = composition,
            iterations = LottieConstants.IterateForever,
            contentScale = ContentScale.Fit
        )
        Text(
            text = stringResource(id = R.string.text_offline),
            style = MaterialTheme.typography.h1,
            color = MaterialTheme.colors.onBackground,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = Padding.Default, bottom = Padding.Small)
        )
        Text(
            text = stringResource(id = R.string.text_offline_description),
            style = MaterialTheme.typography.h5,
            color = MaterialTheme.colors.onBackground,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(vertical = Padding.Default)
        )
    }
}