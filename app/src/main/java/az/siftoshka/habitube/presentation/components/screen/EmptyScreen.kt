package az.siftoshka.habitube.presentation.components.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import az.siftoshka.habitube.R
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition

@Composable
fun EmptyScreen() {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.empty_sand))

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 100.dp)
    ) {
        LottieAnimation(
            modifier = Modifier.height(140.dp),
            composition = composition,
            iterations = LottieConstants.IterateForever,
            contentScale = ContentScale.Fit
        )
    }
}