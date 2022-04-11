package az.siftoshka.habitube.presentation.screens.settings.content

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import az.siftoshka.habitube.R
import az.siftoshka.habitube.presentation.components.TopAppBar
import az.siftoshka.habitube.presentation.theme.spacing

/**
 * Composable function of Content Language Screen.
 */
@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterialApi::class)
@Composable
fun ContentLanguageScreen(
    navController: NavController,
    viewModel: ContentLanguageViewModel = hiltViewModel()
) {
    Surface(color = MaterialTheme.colors.background, modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {
            TopAppBar(
                title = R.string.text_content_language,
                icon = R.drawable.ic_back,
            ) { navController.popBackStack() }
            Spacer(modifier = Modifier.height(16.dp))
            LazyColumn(
                modifier = Modifier
                    .padding(horizontal = MaterialTheme.spacing.default)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                items(languages.size) {
                    val language = languages[it]
                    ContentLanguageRowItem(language) { code ->
                        viewModel.setContentLanguage(code)
                        navController.popBackStack()
                    }
                }
                item {
                    Text(
                        text = stringResource(id = R.string.text_content_warning),
                        style = MaterialTheme.typography.h5,
                        color = MaterialTheme.colors.secondaryVariant,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(MaterialTheme.spacing.default)
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class, ExperimentalComposeUiApi::class)
@Composable
fun ContentLanguageRowItem(
    language: ContentLanguageItem,
    viewModel: ContentLanguageViewModel = hiltViewModel(),
    onPerformClick: (code: String) -> Unit
) {
    Card(
        shape = MaterialTheme.shapes.large,
        backgroundColor = MaterialTheme.colors.surface,
        onClick = { onPerformClick(language.code) },
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
                if (viewModel.getContentLanguage() == language.code) {
                    OutlinedButton(
                        onClick = { onPerformClick(language.code) },
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