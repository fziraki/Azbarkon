package abkabk.azbarkon.utils

import retrofit2.HttpException
import java.io.IOException
import java.net.HttpURLConnection

class GeneralErrorHandlerImpl : ErrorHandler {

    override fun getError(throwable: Throwable): ErrorEntity {
        return when(throwable) {
            is IOException -> ErrorEntity.NoConnection
            is HttpException -> {
                when(throwable.code()) {

                    in 500..503 -> ErrorEntity.ServiceUnavailable

                    504 -> ErrorEntity.NoConnection

                    in 505..599 -> ErrorEntity.ServiceUnavailable

                    HttpURLConnection.HTTP_BAD_REQUEST -> ErrorEntity.BadRequest

                    HttpURLConnection.HTTP_FORBIDDEN -> ErrorEntity.Forbidden

                    HttpURLConnection.HTTP_NOT_FOUND -> ErrorEntity.NotFound

                    // all the others will be treated as unknown error
                    else -> ErrorEntity.Unknown
                }
            }
            else -> ErrorEntity.Unknown
        }
    }
}