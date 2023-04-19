package abkabk.azbarkon.features.poet.domain


import abkabk.azbarkon.core.Constants
import android.os.Parcelable
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize

data class PoetDetails(
    val cat: Cat,
    val poet: Poet
)

@Parcelize
data class Poet(
    val id: Int?,
    val name: String?,
    val description: String?,
    val rootCatId: Int?,
    val imageUrl: String?
): Parcelable {
    @IgnoredOnParcel
    val loadableImageUrl: String by lazy {
        Constants.BASE_URL.plus(imageUrl?.removePrefix("/"))
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