package abkabk.azbarkon.features.entertainment.data.remote

import abkabk.azbarkon.features.entertainment.domain.PoemDetails
import abkabk.azbarkon.features.poet.domain.*
import com.google.gson.annotations.SerializedName

data class PoemDetailsDto(
    @SerializedName("id")
    val id: Int,
    @SerializedName("fullTitle")
    val fullTitle: String?,
    @SerializedName("fullUrl")
    val fullUrl: String?,
    @SerializedName("plainText")
    val plainText: String?,
    @SerializedName("htmlText")
    val htmlText: String?
){
    fun toPoemDetails(): PoemDetails {
        return PoemDetails(
            id = id,
            fullTitle = fullTitle,
            fullUrl = fullUrl,
            plainText = plainText,
            htmlText = htmlText,
            shortTitle = null
        )
    }
}
