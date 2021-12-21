package az.siftoshka.habitube

import android.app.Application
import az.siftoshka.habitube.domain.util.getDeviceLanguage
import com.yariksoffice.lingver.Lingver
import dagger.hilt.android.HiltAndroidApp

/**
 * The [Application] class of the entire app.
 */
@HiltAndroidApp
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        Lingver.init(this, getDeviceLanguage())
    }
}