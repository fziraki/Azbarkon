package abkabk.azbarkon.utils

sealed class Resource<T>(val data: T? = null, val message: String? = null, val error: ErrorEntity? = null) {
    class Success<T>(data: T) : Resource<T>(data)
    class Error<T>(message: String? = null, data: T? = null, error: ErrorEntity? = null) : Resource<T>(data, message, error)
    class Loading<T>(data: T? = null) : Resource<T>(data)
}