package az.siftoshka.habitube.presentation.screens.web

import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import az.siftoshka.habitube.R
import az.siftoshka.habitube.presentation.components.TopAppBar
import az.siftoshka.habitube.presentation.theme.HabitubeV2Theme

/**
 * Composable function of Web screen.
 */
@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterialApi::class)
@Composable
fun WebScreen(value: String, navController: NavController) {

    val title = when (value) {
        "privacy" -> R.string.text_privacy
        "terms" -> R.string.text_terms
        else -> R.string.text_privacy
    }

    val url = when (value) {
        "privacy" -> "file:///android_asset/privacy_policy.html"
        "terms" -> "file:///android_asset/terms_of_service.html"
        else -> "file:///android_asset/privacy_policy.html"
    }

    HabitubeV2Theme {
        Surface(color = MaterialTheme.colors.background, modifier = Modifier.fillMaxSize()) {
            Column(modifier = Modifier.fillMaxSize()) {
                TopAppBar(
                    title = title,
                    icon = R.drawable.ic_back,
                ) { navController.popBackStack() }
                AndroidView(
                    factory = {
                        WebView(it).apply {
                            webViewClient = object : WebViewClient() {
                                override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
                                    return false
                                }
                            }
                        }
                    },
                    update = {
                        it.loadUrl(url)
                    }
                )
            }
        }
    }
}