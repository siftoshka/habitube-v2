package az.siftoshka.habitube.presentation.components

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import az.siftoshka.habitube.R
import az.siftoshka.habitube.presentation.util.Padding

@Composable
fun TopAppBar(
    @StringRes title: Int,
    @DrawableRes icon: Int,
) {
    TopAppBar(
        backgroundColor = MaterialTheme.colors.background,
        elevation = 8.dp,
        title = {
            Text(
                text = stringResource(id = title),
                style = MaterialTheme.typography.h3,
                color = MaterialTheme.colors.onBackground,
                modifier = Modifier
                    .padding(vertical = Padding.Default)
            )
        },
        navigationIcon = {
            Icon(
                painter = painterResource(id = icon),
                contentDescription = stringResource(id = R.string.app_name),
                modifier = Modifier.width(40.dp),
            )
        }
    )
}