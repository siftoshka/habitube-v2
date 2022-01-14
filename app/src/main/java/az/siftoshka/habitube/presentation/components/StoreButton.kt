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
import az.siftoshka.habitube.presentation.theme.spacing

@Composable
fun StoreButton(
    inActiveText: String,
    activeText: String? = null,
    @DrawableRes inActiveIcon: Int,
    @DrawableRes activeIcon: Int? = null,
    isMediaExist: MutableState<Boolean>,
    onPerformClick: (value: Boolean) -> Unit
) {
    val backgroundColor = if (isMediaExist.value) MaterialTheme.colors.primary else MaterialTheme.colors.surface
    val textColor = if (isMediaExist.value) MaterialTheme.colors.onPrimary else MaterialTheme.colors.onSurface
    val text = if (isMediaExist.value) activeText else inActiveText
    val icon = if (isMediaExist.value && activeIcon != null) activeIcon else inActiveIcon

    Button(
        onClick = {
            isMediaExist.value = !isMediaExist.value
            onPerformClick(isMediaExist.value)
        },
        shape = MaterialTheme.shapes.large,
        colors = ButtonDefaults.buttonColors(backgroundColor = backgroundColor),
        modifier = Modifier.padding(MaterialTheme.spacing.extraSmall)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                painter = painterResource(id = icon),
                contentDescription = stringResource(id = R.string.text_planning_watch),
                modifier = Modifier.size(20.dp)
            )
            if (text != null) {
                Text(
                    text = text,
                    style = MaterialTheme.typography.h6,
                    color = textColor,
                    modifier = Modifier
                        .padding(vertical = MaterialTheme.spacing.extraSmall)
                        .padding(start = MaterialTheme.spacing.extraSmall)
                )
            }
        }
    }
}