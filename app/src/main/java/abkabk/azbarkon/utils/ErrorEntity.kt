package abkabk.azbarkon.utils

sealed class ErrorEntity {

    data object NoConnection : ErrorEntity()

    data object ServiceUnavailable : ErrorEntity()

    //invalidID, invalid comment or postID
    data object BadRequest: ErrorEntity()

    data object HTTP_401: ErrorEntity()

    //expired token, invalid refresh token
    data object Forbidden: ErrorEntity()

    //not found
    data object NotFound: ErrorEntity()

    data object Unknown : ErrorEntity()
}