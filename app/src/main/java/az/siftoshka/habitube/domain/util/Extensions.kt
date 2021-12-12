package az.siftoshka.habitube.domain.util

import android.content.Context
import android.content.Intent
import android.net.Uri
import java.text.DecimalFormat

/**
 *  General extension file.
 */
fun String.moneyFormat(): String {
    if (this == "0") return "-"
    return if (contains(".")) DecimalFormat("$#,##0").format(toBigDecimal())
    else DecimalFormat("$#,###").format(toBigDecimal())
}

fun String.onlyYear(): String {
    return this.take(4)
}

fun List<Any?>.toFormattedString(): String {
    return this.toString().replace("[", "").replace("]", "")
}

fun String.takeVideoImageUrl(key: String?): String {
    return when {
        this.contains("YouTube") -> {
            "https://img.youtube.com/vi/${key}/hqdefault.jpg"
        }
        this.contains("Vimeo") -> {
            "https://vumbnail.com/${key}.jpg"
        }
        else -> {
            ""
        }
    }
}

fun Context.openVideo(site: String?, key: String?) {
    val url = when {
        site?.contains("YouTube") == true -> {
            Constants.YOUTUBE_URL + key
        }
        site?.contains("Vimeo") == true -> {
            Constants.VIMEO_URL + key
        }
        else -> return
    }
    startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
}

fun Context.getGithubIntent() = startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(Constants.DEV_GITHUB)))

fun Context.getTelegramIntent() = startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(Constants.DEV_TELEGRAM)))

fun Context.getInstagramIntent() = startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(Constants.DEV_INSTAGRAM)))