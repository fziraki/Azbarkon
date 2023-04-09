package abkabk.azbarkon.features.poet.poet_details

import abkabk.azbarkon.features.poet.domain.Children

data class PoetDetailsState(
    var isLoading: Boolean = false,
    var poetImage: String? = null,
    var poetName: String? = null,
    var catId: Int? = null,
    val children: List<Children>? = emptyList(),
)
