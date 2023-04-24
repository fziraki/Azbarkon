package abkabk.azbarkon.features.poet.poet_list

import abkabk.azbarkon.features.poet.model.PoetUi

data class PoetListState(
    var isLoading: Boolean = false,
    var poetList: List<PoetUi> = emptyList()
)
