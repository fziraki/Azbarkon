package abkabk.azbarkon.features.search.data.repository

import abkabk.azbarkon.features.search.domain.PoemDetails
import abkabk.azbarkon.features.search.data.remote.SearchApi
import abkabk.azbarkon.features.search.domain.repository.SearchRepository
import androidx.paging.PagingSource
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val searchApi: SearchApi
) : SearchRepository {

    override fun searchPoem(
        term: String,
        poetId: Int?,
        catId: Int?
    ): PagingSource<Int, PoemDetails> {
        return SearchPoemByQuerySource(
            searchApi = searchApi,
            term = term,
            poetId = poetId,
            catId = catId
        )
    }

}