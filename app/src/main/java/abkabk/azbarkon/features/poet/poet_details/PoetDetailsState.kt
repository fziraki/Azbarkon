package abkabk.azbarkon.features.poet.poet_details

import abkabk.azbarkon.features.poet.domain.Children
import abkabk.azbarkon.features.poet.model.PoetUi

data class PoetDetailsState(
    var isLoading: Boolean = false,
    var poet: PoetUi? = null,
    var catId: Int? = null,
    var ancestorName: String? = null,
    val children: List<Children>? = null,
)
