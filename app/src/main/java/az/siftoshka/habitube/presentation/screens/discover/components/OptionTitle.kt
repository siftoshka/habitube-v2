package az.siftoshka.habitube.presentation.screens.discover.components

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import az.siftoshka.habitube.presentation.theme.spacing

@Composable
fun OptionTitle(
    @StringRes title: Int
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = MaterialTheme.spacing.small),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Divider(
            modifier = Modifier
                .width(40.dp)
                .padding(horizontal = MaterialTheme.spacing.medium), color = MaterialTheme.colors.onSurface
        )
        Text(
            text = stringResource(id = title),
            style = MaterialTheme.typography.h4,
            color = MaterialTheme.colors.onSurface,
            textAlign = TextAlign.Center
        )
        Divider(
            modifier = Modifier
                .width(40.dp)
                .padding(horizontal = MaterialTheme.spacing.medium), color = MaterialTheme.colors.onSurface
        )
    }
}