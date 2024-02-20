package abkabk.azbarkon.features.poet.domain

import abkabk.azbarkon.features.poet.model.PoetUi
import abkabk.azbarkon.utils.Constants

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
    fun toPoetUi(): PoetUi{
        return PoetUi(
            id = id,
            name = name,
            description = description,
            rootCatId = rootCatId,
            imageUrl = Constants.BASE_URL.plus(imageUrl?.removePrefix("/"))
        )
    }
}

data class Cat(
    val id: Int,
    val title: String,
    val children: List<Children>? = null,
    val ancestors: List<Children>? = null,
    val poems: List<Poem>? = null
)

data class Children(
    val id: Int,
    val title: String,
    val catType: Int
)

data class Poem(
    val id: Int,
    val title: String,
    val excerpt: String
)