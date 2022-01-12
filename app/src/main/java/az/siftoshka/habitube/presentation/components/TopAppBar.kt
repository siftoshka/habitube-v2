package az.siftoshka.habitube.presentation.components

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import az.siftoshka.habitube.R
import az.siftoshka.habitube.presentation.theme.spacing

@Composable
fun TopAppBar(
    @StringRes title: Int,
    @DrawableRes icon: Int,
    onBack: () -> Unit
) {
    TopAppBar(
        backgroundColor = MaterialTheme.colors.background,
        elevation = 8.dp,
        title = {
            Text(
                text = stringResource(id = title),
                style = MaterialTheme.typography.h3,
                color = MaterialTheme.colors.onBackground,
                modifier = Modifier.padding(vertical = MaterialTheme.spacing.default)
            )
        },
        navigationIcon = {
            IconButton(onClick = onBack) {
                Icon(
                    painter = painterResource(id = icon),
                    contentDescription = stringResource(id = R.string.app_name),
                )
            }
        }
    )
}