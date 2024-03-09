package abkabk.azbarkon.features.search.data.remote

import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface SearchApi {

    companion object{
        const val INITIAL_PAGE = 1
        const val GENERAL_PAGE_SIZE = 10
    }

    @Headers("isAuthorizable: false")
    @GET("api/ganjoor/poems/search")
    suspend fun searchPoemsForQuery(
        @Query("PageNumber") page: Int = INITIAL_PAGE,
        @Query("PageSize") size: Int = GENERAL_PAGE_SIZE,
        @Query("term") term: String,
        @Query("poetId") poetId: Int? = 0,
        @Query("catId") catId: Int? = 0
    ) : List<PoemDetailsDto>


}