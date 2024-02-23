package abkabk.azbarkon.features.poem.model

import abkabk.azbarkon.features.poem.domain.PoemDetails

data class PoemDetailsUi(
    val id: Int,
    val fullTitle: String?,
    val fullUrl: String?,
    val plainText: String?,
    val htmlText: String?,
    val shortTitle: String?
){

    fun toPoemDetails(): PoemDetails {
        return PoemDetails(
            id = id,
            fullTitle = fullTitle,
            fullUrl = fullUrl,
            plainText = plainText,
            htmlText = htmlText,
            shortTitle = shortTitle
        )
    }
}

