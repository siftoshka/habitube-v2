package az.siftoshka.habitube.presentation.components

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import az.siftoshka.habitube.presentation.theme.spacing
import az.siftoshka.habitube.presentation.util.SpecialColors

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun UpdateDialog(
    @StringRes title: Int,
    @StringRes text: Int,
    @StringRes textButton: Int,
    state: MutableState<Boolean>,
    onPerformClick: () -> Unit
) {

    if (state.value) {
        AlertDialog(
            shape = RoundedCornerShape(20.dp),
            backgroundColor = SpecialColors.Update,
            onDismissRequest = { onPerformClick() },
            text = {
                Text(
                    text = stringResource(id = text),
                    style = MaterialTheme.typography.body1,
                    color = MaterialTheme.colors.onPrimary,
                )
            },
            title = {
                Text(
                    text = stringResource(id = title),
                    style = MaterialTheme.typography.h2,
                    color = MaterialTheme.colors.onPrimary,
                    modifier = Modifier
                        .verticalScroll(rememberScrollState())
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
                        modifier = Modifier.padding(horizontal = MaterialTheme.spacing.default),
                        backgroundColor = SpecialColors.Update,
                        elevation = 0.dp,
                        onClick = onPerformClick

                    ) {
                        Text(
                            text = stringResource(id = textButton),
                            style = MaterialTheme.typography.body1,
                            color = MaterialTheme.colors.primary,
                            modifier = Modifier.padding(MaterialTheme.spacing.default)
                        )
                    }
                }
            }
        )
    }
}