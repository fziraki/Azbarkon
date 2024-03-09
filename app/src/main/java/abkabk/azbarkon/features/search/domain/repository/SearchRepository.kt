package abkabk.azbarkon.features.search.domain.repository

import abkabk.azbarkon.features.search.domain.PoemDetails
import androidx.paging.PagingSource

interface SearchRepository {
    fun searchPoem(
        term: String,
        poetId: Int?,
        catId: Int?
    ): PagingSource<Int, PoemDetails>

}