package az.siftoshka.habitube.presentation.screens.discover.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import az.siftoshka.habitube.presentation.screens.discover.items.networks
import com.google.accompanist.flowlayout.FlowRow

@Composable
fun NetworkOptions() {
    FlowRow(
        mainAxisSpacing = 8.dp,
        crossAxisSpacing = (-8).dp,
        modifier = Modifier.fillMaxWidth()
    ) { networks.forEach { item -> NetworkTag(item) } }
}