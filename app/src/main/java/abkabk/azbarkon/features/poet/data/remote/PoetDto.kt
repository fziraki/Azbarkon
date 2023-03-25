package abkabk.azbarkon.features.poet.data.remote

import abkabk.azbarkon.features.poet.data.local.PoetEntity
import com.google.gson.annotations.SerializedName

data class PoetDto(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("fullUrl")
    val fullUrl: String?,
    @SerializedName("rootCatId")
    val rootCatId: Int?,
    @SerializedName("nickname")
    val nickname: String?,
    @SerializedName("published")
    val published: Boolean?,
    @SerializedName("imageUrl")
    val imageUrl: String?,
    @SerializedName("birthYearInLHijri")
    val birthYearInLHijri: Int?,
    @SerializedName("validBirthDate")
    val validBirthDate: Boolean?,
    @SerializedName("deathYearInLHijri")
    val deathYearInLHijri: Int?,
    @SerializedName("validDeathDate")
    val validDeathDate: Boolean?,
    @SerializedName("pinOrder")
    val pinOrder: Int?,
    @SerializedName("birthPlace")
    val birthPlace: String?,
    @SerializedName("birthPlaceLatitude")
    val birthPlaceLatitude: Double?,
    @SerializedName("birthPlaceLongitude")
    val birthPlaceLongitude: Double?,
    @SerializedName("deathPlace")
    val deathPlace: String?,
    @SerializedName("deathPlaceLatitude")
    val deathPlaceLatitude: Double?,
    @SerializedName("deathPlaceLongitude")
    val deathPlaceLongitude: Double?
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
}



