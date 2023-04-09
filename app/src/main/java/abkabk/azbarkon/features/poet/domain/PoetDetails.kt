package abkabk.azbarkon.features.poet.domain


import abkabk.azbarkon.core.Constants

data class PoetDetails(
    val cat: Cat,
    val poet: Poet
)

data class Poet(
    val id: Int?,
    val name: String?,
    val description: String?,
    val rootCatId: Int?,
    val imageUrl: String?
){
    val loadableImageUrl: String by lazy {
        Constants.BASE_URL.plus(imageUrl?.removePrefix("/"))
    }
}

data class Cat(
    val id: Int,
    val title: String,
    val children: List<Children>? = null,
    val ancestors: List<Children>? = null
)

data class Children(
    val id: Int,
    val title: String,
    val catType: Int
)