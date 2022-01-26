package az.siftoshka.habitube.presentation.screens.settings.components

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import az.siftoshka.habitube.R
import az.siftoshka.habitube.domain.util.SocialNetworks
import az.siftoshka.habitube.presentation.theme.spacing
import az.siftoshka.habitube.presentation.util.SpecialColors

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ContactMeField(
    @StringRes text: Int,
    onPerformClick: (SocialNetworks) -> Unit,
) {
    Card(
        shape = MaterialTheme.shapes.large,
        backgroundColor = MaterialTheme.colors.surface,
        elevation = 4.dp,
        modifier = Modifier.padding(vertical = MaterialTheme.spacing.extraSmall)
    ) {
        Column(modifier = Modifier.padding(MaterialTheme.spacing.default)) {
            Text(
                text = stringResource(id = text),
                style = MaterialTheme.typography.h4,
                color = MaterialTheme.colors.onSurface,
                modifier = Modifier.padding(bottom = MaterialTheme.spacing.small)
            )
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Card(
                    shape = MaterialTheme.shapes.large,
                    backgroundColor = SpecialColors.Telegram,
                    contentColor = MaterialTheme.colors.onPrimary,
                    onClick = { onPerformClick(SocialNetworks.TELEGRAM) },
                    modifier = Modifier.width(100.dp),
                    elevation = 0.dp
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_telegram),
                        contentDescription = SocialNetworks.TELEGRAM.name,
                        modifier = Modifier.padding(MaterialTheme.spacing.small)
                    )
                }
                Card(
                    shape = MaterialTheme.shapes.large,
                    backgroundColor = SpecialColors.Github,
                    contentColor = MaterialTheme.colors.onPrimary,
                    onClick = { onPerformClick(SocialNetworks.GITHUB) },
                    modifier = Modifier.width(100.dp),
                    elevation = 0.dp
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_github),
                        contentDescription = SocialNetworks.GITHUB.name,
                        modifier = Modifier.padding(MaterialTheme.spacing.small)
                    )
                }
                Card(
                    shape = MaterialTheme.shapes.large,
                    backgroundColor = SpecialColors.Instagram,
                    contentColor = MaterialTheme.colors.onPrimary,
                    onClick = { onPerformClick(SocialNetworks.INSTAGRAM) },
                    modifier = Modifier.width(100.dp),
                    elevation = 0.dp
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_instagram),
                        contentDescription = SocialNetworks.INSTAGRAM.name,
                        modifier = Modifier.padding(MaterialTheme.spacing.small)
                    )
                }
            }
        }
    }
}