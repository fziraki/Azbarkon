package abkabk.azbarkon.features.poet.poet_details

import abkabk.azbarkon.features.poet.domain.Children
import abkabk.azbarkon.features.poet.domain.Poet

data class PoetDetailsState(
    var isLoading: Boolean = false,
    var poet: Poet? = null,
    var catId: Int? = null,
    val children: List<Children>? = null,
)
