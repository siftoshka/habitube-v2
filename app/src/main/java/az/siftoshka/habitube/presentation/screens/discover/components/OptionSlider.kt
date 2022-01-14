package az.siftoshka.habitube.presentation.screens.discover.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun OptionSlider(
    default: ClosedFloatingPointRange<Float>,
    value: ClosedFloatingPointRange<Float>, steps: Int,
    onPerformClick: (value: ClosedFloatingPointRange<Float>) -> Unit,
) {
    var sliderPosition by remember { mutableStateOf(value) }
    RangeSlider(
        steps = steps,
        values = sliderPosition,
        onValueChange = { sliderPosition = it },
        valueRange = default,
        onValueChangeFinished = { onPerformClick(sliderPosition) },
        colors = SliderDefaults.colors(
            thumbColor = MaterialTheme.colors.primary,
            activeTrackColor = MaterialTheme.colors.primary
        )
    )
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        Text(
            text = sliderPosition.start.toInt().toString(),
            color = MaterialTheme.colors.onBackground,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.h5
        )
        Text(
            text = sliderPosition.endInclusive.toInt().toString(),
            color = MaterialTheme.colors.onBackground,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.h5
        )
    }
}