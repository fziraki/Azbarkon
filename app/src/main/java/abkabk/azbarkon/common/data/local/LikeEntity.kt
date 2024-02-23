package abkabk.azbarkon.common.data.local

import abkabk.azbarkon.features.poem.domain.PoemDetails
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class LikeEntity(
    @PrimaryKey val id: Int,
    val fullTitle: String?,
    val fullUrl: String?,
    val plainText: String?,
    val htmlText: String?,
    val shortTitle: String?
) {
    fun toPoemDetails() : PoemDetails{
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