package abkabk.azbarkon.features.poet.model
import abkabk.azbarkon.utils.Constants
import javax.annotation.concurrent.Immutable

@Immutable
data class PoetUi(
    val id: Int?,
    val name: String?,
    val description: String?,
    val rootCatId: Int?,
    val imageUrl: String?
){
    val loadableImageUrl: String by lazy {
        Constants.BASE_URL.plus(imageUrl?.removePrefix("/"))
    }
    var isPinned = false
}