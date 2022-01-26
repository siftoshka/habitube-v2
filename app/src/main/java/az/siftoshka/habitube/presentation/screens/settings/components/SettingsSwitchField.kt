package az.siftoshka.habitube.presentation.screens.settings.components

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.SnackbarDefaults.backgroundColor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import az.siftoshka.habitube.presentation.theme.spacing

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SettingsSwitchField(
    @StringRes text: Int,
    @StringRes description: Int,
    isChecked: MutableState<Boolean>,
    onPerformClick: (Boolean) -> Unit,
) {
    Card(
        shape = MaterialTheme.shapes.large,
        backgroundColor = MaterialTheme.colors.surface,
        elevation = 4.dp,
        onClick = { onPerformClick(!isChecked.value) },
        modifier = Modifier.padding(vertical = MaterialTheme.spacing.extraSmall)
    ) {
        ListItem(
            text = {
                Text(
                    text = stringResource(id = text),
                    style = MaterialTheme.typography.h4,
                    color = MaterialTheme.colors.onSurface,
                )
            },
            secondaryText = {
                Text(
                    text = stringResource(id = description),
                    style = MaterialTheme.typography.body2,
                    color = MaterialTheme.colors.secondaryVariant,
                    modifier = Modifier.padding(vertical = MaterialTheme.spacing.small)
                )
            },
            trailing = {
                Switch(
                    checked = isChecked.value,
                    onCheckedChange = { onPerformClick(it) },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = MaterialTheme.colors.primary,
                        uncheckedThumbColor = MaterialTheme.colors.onPrimary
                    )
                )
            }
        )
    }
}