package abkabk.azbarkon.features.poet.data.remote


import abkabk.azbarkon.features.poet.data.local.PoetEntity
import abkabk.azbarkon.features.poet.domain.*
import com.google.gson.annotations.SerializedName

data class PoetDetailsDto(
    @SerializedName("cat")
    val cat: CatDto,
    @SerializedName("poet")
    val poet: PoetDto
){
    fun toPoetDetails(): PoetDetails {
        return PoetDetails(
            cat = cat.toCat(),
            poet = poet.toPoet()
        )
    }
}

data class PoetDto(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("rootCatId")
    val rootCatId: Int?,
    @SerializedName("imageUrl")
    val imageUrl: String?
){
    fun toPoetEntity(): PoetEntity {
        return PoetEntity(
            id = id,
            name = name,
            description = description,
            rootCatId = rootCatId,
            imageUrl = imageUrl
        )
    }

    fun toPoet(): Poet {
        return Poet(
            id = id,
            name = name,
            description = description,
            rootCatId = rootCatId,
            imageUrl = imageUrl
        )
    }
}

data class CatDto(
    @SerializedName("id")
    val id: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("children")
    val children: List<ChildrenDto>? = null,
    @SerializedName("ancestors")
    val ancestors: List<ChildrenDto>? = null,
    @SerializedName("poems")
    val poems: List<PoemDto>? = null
){
    fun toCat(): Cat{
        return Cat(
            id = id,
            title = title,
            children = children?.map { it.toChildren() },
            ancestors = ancestors?.map { it.toChildren() },
            poems = poems?.map { it.toPoem() }
        )
    }

}

data class ChildrenDto(
    @SerializedName("id")
    val id: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("catType")
    val catType: Int
){
    fun toChildren(): Children {
        return Children(
            id = id,
            title = title,
            catType = catType
        )
    }

}

data class PoemDto(
    @SerializedName("id")
    val id: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("excerpt")
    val excerpt: String
){
    fun toPoem(): Poem {
        return Poem(
            id = id,
            title = title,
            excerpt = excerpt
        )
    }

}