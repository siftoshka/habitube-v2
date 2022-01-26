package az.siftoshka.habitube.presentation.screens.settings.components

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import az.siftoshka.habitube.presentation.theme.spacing

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SettingsSimpleButtonField(
    @StringRes text: Int,
    onPerformClick: () -> Unit
) {
    Card(
        shape = MaterialTheme.shapes.large,
        backgroundColor = MaterialTheme.colors.surface,
        onClick = { onPerformClick() },
        elevation = 4.dp,
        modifier = Modifier.padding(vertical = MaterialTheme.spacing.extraSmall)
    ) {
        ListItem(
            text = {
                Text(
                    text = stringResource(id = text),
                    style = MaterialTheme.typography.h4,
                    color = MaterialTheme.colors.onSurface,
                )
            }
        )
    }
}