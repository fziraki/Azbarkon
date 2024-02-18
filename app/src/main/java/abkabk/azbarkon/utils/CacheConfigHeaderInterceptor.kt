package abkabk.azbarkon.utils

import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class CacheConfigHeaderInterceptor @Inject constructor(): Interceptor {

    companion object {
        const val HEADER_CACHE = "android-cache"
    }

    override fun intercept(chain: Interceptor.Chain): Response {
            val request = chain.request()
            if (request.header(HEADER_CACHE) != null) {
                val offlineRequest = request.newBuilder()
                    .header("Cache-Control", "only-if-cached, " +
                            "max-stale=" + request.header(HEADER_CACHE))
                    .build()
                val response = chain.proceed(offlineRequest)
                if (response.isSuccessful) {
                    return response
                }
            }
        return try {
            chain.proceed(chain.request())
        } catch (e: Exception) {
            val offlineRequest = request.newBuilder()
                .header("Cache-Control", "public, only-if-cached, " +
                        "max-stale=" + 60 * 60 * 24)
                .build()
            chain.proceed(offlineRequest)
        }

    }

}