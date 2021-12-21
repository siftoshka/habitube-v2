package az.siftoshka.habitube.presentation.screens.settings

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import az.siftoshka.habitube.R
import az.siftoshka.habitube.domain.util.*
import az.siftoshka.habitube.presentation.components.text.SettingsTitleText
import az.siftoshka.habitube.presentation.theme.HabitubeV2Theme
import az.siftoshka.habitube.presentation.util.Padding
import az.siftoshka.habitube.presentation.util.Screen
import az.siftoshka.habitube.presentation.util.SocialColors

/**
 * Composable function of the Settings screen.
 */
@Composable
fun SettingsScreen(
    navController: NavController
) {
    val scrollState = rememberScrollState()
    val context = LocalContext.current

    HabitubeV2Theme {
        Surface(color = MaterialTheme.colors.background, modifier = Modifier.fillMaxSize()) {
            Column(modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)) {
                Column(Modifier.padding(horizontal = Padding.Default)) {
                    SettingsTitleText(text = R.string.text_general)
                    ButtonItem(
                        text = R.string.text_language,
                        getCurrentLanguage()
                    ) { navController.navigate(Screen.LanguageScreen.route) }
                    Spacer(modifier = Modifier.height(48.dp))
                    SettingsTitleText(text = R.string.text_about)
                    TextItem(text = R.string.text_app_version, secondaryText = R.string.version_name)
                    ContactMeField(text = R.string.text_contact_me) {
                        when (it) {
                            SocialNetworks.TELEGRAM -> context.getTelegramIntent()
                            SocialNetworks.GITHUB -> context.getGithubIntent()
                            SocialNetworks.INSTAGRAM -> context.getInstagramIntent()
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ButtonItem(
    @StringRes text: Int,
    language: String,
    onPerformClick: () -> Unit
) {
    Card(
        shape = MaterialTheme.shapes.large,
        backgroundColor = MaterialTheme.colors.surface,
        onClick = { onPerformClick() },
        elevation = 4.dp,
        modifier = Modifier.padding(vertical = Padding.ExtraSmall)
    ) {
        ListItem(
            text = {
                Text(
                    text = stringResource(id = text),
                    style = MaterialTheme.typography.h4,
                    color = MaterialTheme.colors.onSurface,
                )
            },
            trailing = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = language,
                        style = MaterialTheme.typography.body2,
                        color = MaterialTheme.colors.secondaryVariant,
                    )
                    IconButton(onClick = onPerformClick) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_forward),
                            contentDescription = language,
                        )
                    }
                }
            }
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun TextItem(
    @StringRes text: Int,
    @StringRes secondaryText: Int
) {
    Card(
        shape = MaterialTheme.shapes.large,
        backgroundColor = MaterialTheme.colors.surface,
        elevation = 4.dp,
        modifier = Modifier.padding(vertical = Padding.ExtraSmall)
    ) {
        ListItem(
            text = {
                Text(
                    text = stringResource(id = text),
                    style = MaterialTheme.typography.h4,
                    color = MaterialTheme.colors.onSurface,
                )
            },
            trailing = {
                Text(
                    text = stringResource(id = secondaryText),
                    style = MaterialTheme.typography.body2,
                    color = MaterialTheme.colors.secondaryVariant,
                    modifier = Modifier.padding(vertical = Padding.Small)
                )
            }
        )
    }
}

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
        modifier = Modifier.padding(vertical = Padding.ExtraSmall)
    ) {
        Column(modifier = Modifier.padding(Padding.Default)) {
            Text(
                text = stringResource(id = text),
                style = MaterialTheme.typography.h4,
                color = MaterialTheme.colors.onSurface,
                modifier = Modifier.padding(bottom = Padding.Small)
            )
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Card(
                    shape = MaterialTheme.shapes.large,
                    backgroundColor = SocialColors.Telegram,
                    contentColor = MaterialTheme.colors.onPrimary,
                    onClick = { onPerformClick(SocialNetworks.TELEGRAM) },
                    modifier = Modifier.width(100.dp),
                    elevation = 0.dp
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_telegram),
                        contentDescription = SocialNetworks.TELEGRAM.name,
                        modifier = Modifier.padding(Padding.Small)
                    )
                }
                Card(
                    shape = MaterialTheme.shapes.large,
                    backgroundColor = SocialColors.Github,
                    contentColor = MaterialTheme.colors.onPrimary,
                    onClick = { onPerformClick(SocialNetworks.GITHUB) },
                    modifier = Modifier.width(100.dp),
                    elevation = 0.dp
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_github),
                        contentDescription = SocialNetworks.GITHUB.name,
                        modifier = Modifier.padding(Padding.Small)
                    )
                }
                Card(
                    shape = MaterialTheme.shapes.large,
                    backgroundColor = SocialColors.Instagram,
                    contentColor = MaterialTheme.colors.onPrimary,
                    onClick = { onPerformClick(SocialNetworks.INSTAGRAM) },
                    modifier = Modifier.width(100.dp),
                    elevation = 0.dp
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_instagram),
                        contentDescription = SocialNetworks.INSTAGRAM.name,
                        modifier = Modifier.padding(Padding.Small)
                    )
                }
            }
        }
    }
}