package abkabk.azbarkon.features.poem.poem_details

import abkabk.azbarkon.features.poem.model.PoemDetailsUi

data class PoemDetailsState(
    var isLoading: Boolean = false,
    var poemDetails: PoemDetailsUi? = null,
    var poemTitle: String? = "",
    var isLiked: Boolean = false
)
