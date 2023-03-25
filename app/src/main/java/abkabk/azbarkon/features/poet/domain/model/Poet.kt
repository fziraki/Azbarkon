package abkabk.azbarkon.features.poet.domain.model

import abkabk.azbarkon.core.Constants.BASE_URL

data class Poet(
    val id: Int?,
    val name: String?,
    val description: String?,
    val rootCatId: Int?,
    val imageUrl: String?
){
    val loadableImageUrl: String by lazy { BASE_URL.plus(imageUrl?.removePrefix("/")) }
}