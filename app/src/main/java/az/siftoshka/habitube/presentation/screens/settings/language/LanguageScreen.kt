package az.siftoshka.habitube.presentation.screens.settings.language

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import az.siftoshka.habitube.R
import az.siftoshka.habitube.domain.util.*
import az.siftoshka.habitube.presentation.components.TopAppBar
import az.siftoshka.habitube.presentation.theme.spacing

/**
 * Composable function of Language Screen.
 */
@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterialApi::class)
@Composable
fun LanguageScreen(
    navController: NavController
) {
    val context = LocalContext.current

    Surface(color = MaterialTheme.colors.background, modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {
            TopAppBar(
                title = R.string.text_language,
                icon = R.drawable.ic_back,
            ) { navController.popBackStack() }
            Spacer(modifier = Modifier.height(16.dp))
            LazyColumn(
                modifier = Modifier
                    .padding(horizontal = MaterialTheme.spacing.default)
                    .fillMaxWidth()
            ) {
                items(languages.size) {
                    val language = languages[it]
                    LanguageRowItem(language) { category ->
                        when (category) {
                            LanguageCategory.AUTO -> {
                                context.updateLanguage(getDeviceLanguage(), getDeviceCountry())
                            }
                            LanguageCategory.ENGLISH -> {
                                context.updateLanguage(Language.ENGLISH.language, Language.ENGLISH.country)
                            }
                            LanguageCategory.AZERBAIJANI -> {
                                context.updateLanguage(Language.AZERBAIJANI.language, Language.AZERBAIJANI.country)
                            }
                            LanguageCategory.FRENCH -> {
                                context.updateLanguage(Language.FRENCH.language, Language.FRENCH.country)
                            }
                            LanguageCategory.SPANISH -> {
                                context.updateLanguage(Language.SPANISH.language, Language.SPANISH.country)
                            }
                            LanguageCategory.RUSSIAN -> {
                                context.updateLanguage(Language.RUSSIAN.language, Language.RUSSIAN.country)
                            }
                        }
                        navController.popBackStack()
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class, ExperimentalComposeUiApi::class)
@Composable
fun LanguageRowItem(
    language: LanguageItem,
    onPerformClick: (LanguageCategory) -> Unit
) {
    Card(
        shape = MaterialTheme.shapes.large,
        backgroundColor = MaterialTheme.colors.surface,
        onClick = { onPerformClick(language.category) },
        elevation = 4.dp,
        modifier = Modifier.padding(vertical = MaterialTheme.spacing.extraSmall)
    ) {
        ListItem(
            text = {
                Text(
                    text = stringResource(id = language.text),
                    style = MaterialTheme.typography.h4,
                    color = MaterialTheme.colors.onSurface,
                )
            },
            trailing = {
                if (getCurrentLanguageCode() == language.code) {
                    OutlinedButton(
                        onClick = { onPerformClick(language.category) },
                        shape = RoundedCornerShape(20.dp),
                        border = BorderStroke(1.dp, MaterialTheme.colors.primary),
                        colors = ButtonDefaults.outlinedButtonColors(
                            backgroundColor = Color.Transparent,
                            contentColor = MaterialTheme.colors.primary
                        )
                    ) {
                        Text(
                            text = stringResource(id = R.string.text_selected),
                            style = MaterialTheme.typography.body2,
                            color = MaterialTheme.colors.primary
                        )
                    }
                }
            }
        )
    }
}