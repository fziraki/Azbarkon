package abkabk.azbarkon.features.poet.poet_list

import abkabk.azbarkon.features.poet.domain.Poet

data class PoetListState(
    var isLoading: Boolean = false,
    var poetList: List<Poet> = emptyList()
)
