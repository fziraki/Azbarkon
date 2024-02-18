package abkabk.azbarkon.utils

interface ErrorHandler {

    fun getError(throwable: Throwable): ErrorEntity
}