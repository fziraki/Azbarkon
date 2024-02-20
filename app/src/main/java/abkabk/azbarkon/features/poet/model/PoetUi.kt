package abkabk.azbarkon.features.poet.model
import androidx.compose.runtime.Immutable

@Immutable
data class PoetUi(
    val id: Int?,
    val name: String?,
    val description: String?,
    val rootCatId: Int?,
    val imageUrl: String?,
    var isPinned: Boolean = false,
    var isSelected: Boolean = false
)