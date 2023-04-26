package abkabk.azbarkon.features.likes

import abkabk.azbarkon.features.poem.model.PoemDetailsUi

data class LikesState(
    var isLoading: Boolean = false,
    var poemList: List<PoemDetailsUi>? = emptyList()
)
