package abkabk.azbarkon.utils

import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor() : Interceptor {

    companion object{
        private const val AUTHORIZATION_HEADER = "Authorization"
        private const val ACCESS_TOKEN = "ACCESS_TOKEN"
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        synchronized(this) {
            val original = chain.request()
            //add this header to apis which doest need Authorization header
            val shouldAddAuthHeaders = original.headers["isAuthorizable"] != "false"
            val builder = original.newBuilder().removeHeader("isAuthorizable")
            //check whether api call needs Authorization header or not
            if (shouldAddAuthHeaders) {
                builder.addHeader(AUTHORIZATION_HEADER, "Bearer $ACCESS_TOKEN")
            }
            return chain.proceed(builder.build())
        }
    }
}