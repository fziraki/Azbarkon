package abkabk.azbarkon.features.search.domain

import abkabk.azbarkon.common.data.local.LikeEntity
import abkabk.azbarkon.features.poem.model.PoemDetailsUi

data class PoemDetails(
    val id: Int,
    val fullTitle: String?,
    val fullUrl: String?,
    val plainText: String?,
    val htmlText: String?,
    var shortTitle: String?
) {
    fun toPoemDetailsUi(): PoemDetailsUi {
        return PoemDetailsUi(
            id = id,
            fullTitle = fullTitle,
            fullUrl = fullUrl,
            plainText = plainText,
            htmlText = htmlText,
            shortTitle = shortTitle
        )
    }

    fun toLikeEntity(): LikeEntity {
        return LikeEntity(
            id = id,
            fullTitle = fullTitle,
            fullUrl = fullUrl,
            plainText = plainText,
            htmlText = htmlText,
            shortTitle = shortTitle
        )
    }
}

