package abkabk.azbarkon.features.poet.data.remote

import abkabk.azbarkon.utils.CacheConfigHeaderInterceptor.Companion.HEADER_CACHE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface PoetApi {

    @Headers("isAuthorizable: false", "${HEADER_CACHE}: 60 * 60 * 24")
    @GET("api/ganjoor/poets")
    suspend fun getPoetList() : List<PoetDto>

    @Headers("isAuthorizable: false", "${HEADER_CACHE}: 60 * 60 * 24")
    @GET("api/ganjoor/cat/{catId}")
    suspend fun getSubCategories(
        @Path("catId") catId: Int,
        @Query("poems") poems: Boolean = true,
        @Query("mainSections") mainSections: Boolean = false
    ) : PoetDetailsDto
}