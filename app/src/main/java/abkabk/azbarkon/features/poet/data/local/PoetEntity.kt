package abkabk.azbarkon.features.poet.data.local

import abkabk.azbarkon.features.poet.domain.model.Poet
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PoetEntity(
    @PrimaryKey val id: Int?,
    val name: String?,
    val description: String?,
    val rootCatId: Int?,
    val imageUrl: String?
){

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
