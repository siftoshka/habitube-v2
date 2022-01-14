package az.siftoshka.habitube.presentation.screens.home.dialog

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import az.siftoshka.habitube.presentation.theme.spacing
import az.siftoshka.habitube.presentation.util.SpecialColors
import nl.dionsegijn.konfetti.compose.KonfettiView

@OptIn(ExperimentalMaterialApi::class, ExperimentalComposeUiApi::class)
@Composable
fun UpdateDialog(
    @StringRes title: Int,
    @StringRes text: Int,
    @StringRes textButton: Int,
    state: MutableState<Boolean>,
    viewModel: UpdateDialogViewModel = hiltViewModel(),
    onPerformClick: () -> Unit
) {

    if (state.value) {
        Dialog(onDismissRequest = { onPerformClick() }, properties = DialogProperties(usePlatformDefaultWidth = false)) {
            Box(
                Modifier
                    .fillMaxSize()
                    .clickable(indication = null, interactionSource = MutableInteractionSource()) { onPerformClick() }) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = MaterialTheme.spacing.ultraLarge),
                    verticalArrangement = Arrangement.Center
                ) {
                    Column(
                        modifier = Modifier
                            .clip(RoundedCornerShape(20.dp))
                            .background(SpecialColors.Update)
                            .padding(MaterialTheme.spacing.default)
                    ) {
                        Text(
                            text = stringResource(id = title),
                            style = MaterialTheme.typography.h2,
                            color = MaterialTheme.colors.onPrimary,
                        )
                        Spacer(modifier = Modifier.height(MaterialTheme.spacing.default))
                        Text(
                            text = stringResource(id = text),
                            style = MaterialTheme.typography.body1,
                            color = MaterialTheme.colors.onPrimary,
                        )
                        Spacer(modifier = Modifier.height(MaterialTheme.spacing.large))
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                            Card(
                                shape = MaterialTheme.shapes.large,
                                backgroundColor = SpecialColors.Update,
                                elevation = 0.dp,
                                onClick = onPerformClick

                            ) {
                                Text(
                                    text = stringResource(id = textButton),
                                    style = MaterialTheme.typography.body1,
                                    color = MaterialTheme.colors.primary,
                                    modifier = Modifier.padding(MaterialTheme.spacing.medium)
                                )
                            }
                        }
                    }
                }
                KonfettiView(
                    parties = viewModel.parade(viewModel.colors.random()),
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}