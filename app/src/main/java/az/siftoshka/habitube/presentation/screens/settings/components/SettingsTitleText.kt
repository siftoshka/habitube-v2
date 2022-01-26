package az.siftoshka.habitube.presentation.screens.settings.components

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import az.siftoshka.habitube.presentation.theme.spacing

@Composable
fun SettingsTitleText(
    @StringRes text: Int,
) {
    Text(
        text = stringResource(id = text),
        style = MaterialTheme.typography.h1,
        color = MaterialTheme.colors.onBackground,
        textAlign = TextAlign.Start,
        modifier = Modifier.padding(vertical = MaterialTheme.spacing.extraSmall)
    )
}