package abkabk.azbarkon.features.poet.domain

import abkabk.azbarkon.features.poet.model.PoetUi

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
            imageUrl = imageUrl
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