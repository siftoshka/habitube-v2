package az.siftoshka.habitube.presentation.screens.settings.components

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import az.siftoshka.habitube.presentation.theme.spacing

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun FoldableTextField(
    @StringRes shortText: Int,
    @StringRes longText: Int
) {
    var short by remember { mutableStateOf(true) }

    Card(
        shape = MaterialTheme.shapes.large,
        backgroundColor = MaterialTheme.colors.surface,
        elevation = 4.dp,
        onClick = { short = !short },
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = MaterialTheme.spacing.extraSmall)
    ) {
        Text(
            text = stringResource(id = shortText),
            style = MaterialTheme.typography.h4,
            color = MaterialTheme.colors.onSurface,
            textAlign = TextAlign.Start,
            modifier = Modifier.padding(MaterialTheme.spacing.default)
        )
        if (!short) {
            Text(
                text = "\n\n" + stringResource(id = longText),
                style = MaterialTheme.typography.body2,
                color = MaterialTheme.colors.secondaryVariant,
                textAlign = TextAlign.Start,
                modifier = Modifier.padding(MaterialTheme.spacing.default)
            )
        }
    }
}