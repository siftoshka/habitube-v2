package az.siftoshka.habitube.presentation.screens.settings.fields

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import az.siftoshka.habitube.presentation.util.Padding

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
        modifier = Modifier.padding(vertical = Padding.ExtraSmall)
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
                    modifier = Modifier.padding(vertical = Padding.Small)
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