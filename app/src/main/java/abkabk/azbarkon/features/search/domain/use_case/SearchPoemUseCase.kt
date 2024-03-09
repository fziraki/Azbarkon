package abkabk.azbarkon.features.search.domain.use_case

import abkabk.azbarkon.features.search.domain.PoemDetails
import abkabk.azbarkon.features.search.domain.repository.SearchRepository
import androidx.paging.PagingSource
import javax.inject.Inject

class SearchPoemUseCase @Inject constructor(
    private val searchRepository: SearchRepository
) {
    operator fun invoke(
        term: String,
        poetId: Int?,
        catId: Int?
    ): PagingSource<Int, PoemDetails> {
        return searchRepository.searchPoem(
            term = term,
            poetId = poetId,
            catId = catId
        )
    }
}