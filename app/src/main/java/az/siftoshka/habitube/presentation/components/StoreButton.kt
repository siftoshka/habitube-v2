package az.siftoshka.habitube.presentation.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
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
    isMediaExist: MutableState<Boolean>,
    onPerformClick: (value: Boolean) -> Unit
) {
    val backgroundColor = if (isMediaExist.value) MaterialTheme.colors.primary else MaterialTheme.colors.surface
    val text = if (isMediaExist.value) activeText else inActiveText

    Button(
        onClick = {
            isMediaExist.value = !isMediaExist.value
            onPerformClick(isMediaExist.value)
        },
        shape = MaterialTheme.shapes.large,
        colors = ButtonDefaults.buttonColors(backgroundColor = backgroundColor),
        modifier = Modifier.padding(Padding.ExtraSmall)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                painter = painterResource(id = icon),
                contentDescription = stringResource(id = R.string.text_planning_watch),
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