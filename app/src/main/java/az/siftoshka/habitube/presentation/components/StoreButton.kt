package az.siftoshka.habitube.presentation.components

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import az.siftoshka.habitube.R
import az.siftoshka.habitube.presentation.util.Padding

@Composable
fun StoreButton(
    inActiveText: String,
    activeText: String,
    @DrawableRes icon: Int,
    isMovieExist: Boolean,
    onPerformClick: (value: Boolean) -> Unit
) {
    val isExist = remember { mutableStateOf(isMovieExist) }
    val backgroundColor = if (isExist.value) MaterialTheme.colors.primary else MaterialTheme.colors.surface
    val text = if (isExist.value) activeText else inActiveText

    Button(
        onClick = {
            isExist.value = !isExist.value
            onPerformClick(isExist.value)
        },
        shape = MaterialTheme.shapes.large,
        colors = ButtonDefaults.buttonColors(backgroundColor = backgroundColor),
        modifier = Modifier.padding(Padding.ExtraSmall)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                painter = painterResource(id = icon),
                contentDescription = stringResource(id = R.string.text_watch_later),
                modifier = Modifier
                    .padding(end = Padding.ExtraSmall)
                    .size(20.dp)
            )
            Text(
                text = text,
                style = MaterialTheme.typography.h6,
                color = MaterialTheme.colors.onBackground,
                modifier = Modifier.padding(vertical = Padding.Small)
            )
        }
    }
}