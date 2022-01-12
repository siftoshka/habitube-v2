package az.siftoshka.habitube.presentation.screens.settings.storage

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import az.siftoshka.habitube.R
import az.siftoshka.habitube.presentation.theme.spacing

@ExperimentalMaterialApi
@Composable
fun StorageDialog(
    @StringRes title: Int,
    @StringRes text: Int,
    @StringRes positiveButton: Int,
    state: MutableState<Boolean>,
    onPerformClick: () -> Unit,
    onCancel: () -> Unit
) {
    if (state.value) {
        AlertDialog(
            shape = RoundedCornerShape(20.dp),
            modifier = Modifier.background(MaterialTheme.colors.surface, RoundedCornerShape(20.dp)),
            onDismissRequest = { state.value = false },
            text = {
                Text(
                    text = stringResource(id = text),
                    style = MaterialTheme.typography.body1,
                    color = MaterialTheme.colors.onSurface,
                )
            },
            title = {
                Text(
                    text = stringResource(id = title),
                    style = MaterialTheme.typography.h2,
                    color = MaterialTheme.colors.onSurface,
                )
            },
            buttons = {
                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = MaterialTheme.spacing.default)
                ) {
                    Card(
                        shape = MaterialTheme.shapes.large,
                        backgroundColor = MaterialTheme.colors.surface,
                        elevation = 0.dp,
                        onClick = onCancel
                    ) {
                        Text(
                            text = stringResource(id = R.string.text_cancel),
                            style = MaterialTheme.typography.body1,
                            color = MaterialTheme.colors.onSurface,
                            modifier = Modifier.padding(MaterialTheme.spacing.default)
                        )
                    }
                    Card(
                        shape = MaterialTheme.shapes.large,
                        modifier = Modifier.padding(horizontal = MaterialTheme.spacing.default),
                        backgroundColor = MaterialTheme.colors.surface,
                        elevation = 0.dp,
                        onClick = onPerformClick
                    ) {
                        Text(
                            text = stringResource(id = positiveButton),
                            style = MaterialTheme.typography.body1,
                            color = MaterialTheme.colors.error,
                            modifier = Modifier.padding(MaterialTheme.spacing.default)
                        )
                    }
                }
            }
        )
    }
}