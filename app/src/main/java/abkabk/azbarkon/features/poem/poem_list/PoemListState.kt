package abkabk.azbarkon.features.poem.poem_list

import abkabk.azbarkon.features.poet.domain.Poem

data class PoemListState(
    var isLoading: Boolean = false,
    var ancestorName: String? = null,
    var poemList: List<Poem>? = emptyList()
)
