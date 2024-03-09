package abkabk.azbarkon.features.search.data.repository

import abkabk.azbarkon.features.search.data.remote.SearchApi
import abkabk.azbarkon.features.search.domain.PoemDetails
import androidx.paging.PagingSource
import androidx.paging.PagingState
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class SearchPoemByQuerySource @Inject constructor(
    private val searchApi: SearchApi,
    private val term: String,
    private val poetId: Int? = null,
    private val catId: Int? = null
): PagingSource<Int, PoemDetails>() {

    companion object {
        const val INITIAL_PAGE = 1
        const val PAGE_SIZE = 15
        const val PREFETCH_DISTANCE = 1
    }

    override fun getRefreshKey(state: PagingState<Int, PoemDetails>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PoemDetails> {
        return try {
            val page = params.key ?: INITIAL_PAGE
            val poemsByTerm =
                searchApi.searchPoemsForQuery(
                    page = page,
                    size = PAGE_SIZE,
                    term = term,
                    poetId = poetId,
                    catId = catId
                ).map { it.toPoemDetails() }
            LoadResult.Page(
                data = poemsByTerm,
                prevKey = if (page == INITIAL_PAGE) null else page.dec(),
                nextKey = if (poemsByTerm.size < PAGE_SIZE) null else page.inc()
            )
        } catch (e: HttpException) {
            LoadResult.Error(e)
        } catch (e: IOException) {
            LoadResult.Error(e)
        }
    }
}