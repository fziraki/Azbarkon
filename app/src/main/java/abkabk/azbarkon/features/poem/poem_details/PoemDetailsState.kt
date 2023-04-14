package abkabk.azbarkon.features.poem.poem_details

import abkabk.azbarkon.features.poem.domain.PoemDetails

data class PoemDetailsState(
    var isLoading: Boolean = false,
    var poemDetails: PoemDetails? = null
)
