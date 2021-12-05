package az.siftoshka.habitube.presentation

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.navigation.compose.rememberNavController
import az.siftoshka.habitube.presentation.theme.HabitubeV2Theme
import dagger.hilt.android.AndroidEntryPoint

/**
 * The MainActivity of the app. There is only single activity.
 */
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HabitubeV2Theme {
                Surface(color = MaterialTheme.colors.background) {
                    val navController = rememberNavController()

                }
            }
        }
    }
}