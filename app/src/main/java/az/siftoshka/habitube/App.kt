package az.siftoshka.habitube

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * The [Application] class of the entire app.
 */
@HiltAndroidApp
class App : Application() {

    override fun onCreate() {
        super.onCreate()
    }
}