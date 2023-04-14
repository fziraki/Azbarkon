package abkabk.azbarkon.features.poet.poet_details

import abkabk.azbarkon.features.poet.domain.Children
import abkabk.azbarkon.features.poet.domain.Poem

data class PoetDetailsState(
    var isLoading: Boolean = false,
    var poetImage: String? = null,
    var poetName: String? = null,
    var catId: Int? = null,
    var childId: Int? = null,
    val children: List<Children>? = emptyList(),
    val poems: List<Poem>? = emptyList()
)
