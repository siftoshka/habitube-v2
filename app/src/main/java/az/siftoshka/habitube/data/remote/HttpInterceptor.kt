package az.siftoshka.habitube.data.remote

import android.text.TextUtils
import az.siftoshka.habitube.BuildConfig
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

/**
 * Interceptor of our project.
 */
class HttpInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = chain.request()
        val sign: String = if (TextUtils.isEmpty(request.url.encodedQuery)) "?" else "&"
        val newRequest: Request.Builder = request.newBuilder().url(request.url.toString() + sign + "api_key=" + BuildConfig.THEMOVIEDB_API_KEY)
        return chain.proceed(newRequest.build())
    }
}