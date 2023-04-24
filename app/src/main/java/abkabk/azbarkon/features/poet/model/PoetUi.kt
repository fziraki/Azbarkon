package abkabk.azbarkon.features.poet.model
import abkabk.azbarkon.common.Constants
import android.os.Parcelable
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize

@Parcelize
data class PoetUi(
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
    @IgnoredOnParcel
    var isPinned = false
}