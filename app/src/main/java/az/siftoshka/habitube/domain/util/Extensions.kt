package az.siftoshka.habitube.domain.util

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import az.siftoshka.habitube.R
import coil.ImageLoader
import coil.request.ImageRequest
import java.io.File
import java.io.FileOutputStream
import java.text.DecimalFormat


/**
 *  General extension file.
 */
fun String.moneyFormat(): String {
    if (this == "0") return "-"
    return if (contains(".")) DecimalFormat("$#,##0").format(toBigDecimal())
    else DecimalFormat("$#,###").format(toBigDecimal())
}

fun String?.normalDate(context: Context): String {
    val year = this?.substring(0, 4).orEmpty()
    var month = this?.substring(5, 7).orEmpty()
    val day = this?.substring(8, 10).orEmpty()

    when (month) {
        "01" -> month = context.getString(R.string.month_1)
        "02" -> month = context.getString(R.string.month_2)
        "03" -> month = context.getString(R.string.month_3)
        "04" -> month = context.getString(R.string.month_4)
        "05" -> month = context.getString(R.string.month_5)
        "06" -> month = context.getString(R.string.month_6)
        "07" -> month = context.getString(R.string.month_7)
        "08" -> month = context.getString(R.string.month_8)
        "09" -> month = context.getString(R.string.month_9)
        "10" -> month = context.getString(R.string.month_10)
        "11" -> month = context.getString(R.string.month_11)
        "12" -> month = context.getString(R.string.month_12)
    }

    return "$day $month $year"
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

fun Context.saveToStorage(imageDir: String?, isWatched: Boolean) {
    val imagePath = imageDir?.replace("/", "-")
    val configText = if (isWatched) "/watched" else "/planned"
    var fos: FileOutputStream? = null
    val loader = ImageLoader(this)
    val request = ImageRequest.Builder(this).data(Constants.IMAGE_URL + imageDir).target {
        val bitmapImage = (it as BitmapDrawable).bitmap
        try {
            val myPath = File(this.filesDir.path + File.separator.toString() + configText + imagePath)
            fos = FileOutputStream(myPath)
            bitmapImage.compress(Bitmap.CompressFormat.JPEG, 60, fos)
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            try {
                fos?.close()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }.build()

    loader.enqueue(request)
}

fun Context.deleteFromStorage(imageDir: String?, isWatched: Boolean) {
    val imagePath = imageDir?.replace("/", "-")
    val configText = if (isWatched) "/watched" else "/planned"
    File(this.filesDir.path + configText + imagePath).delete()
}

fun Context.renameFileToWatched(imageDir: String?) {
    val imagePath = imageDir?.replace("/", "-")
    File(this.filesDir.path + "/planned" + imagePath)
        .renameTo(File(this.filesDir.path + "/watched" + imagePath))
}

fun Context.getGithubIntent() = startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(Constants.DEV_GITHUB)))

fun Context.getTelegramIntent() = startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(Constants.DEV_TELEGRAM)))

fun Context.getInstagramIntent() = startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(Constants.DEV_INSTAGRAM)))