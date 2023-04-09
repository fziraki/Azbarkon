package abkabk.azbarkon.features.poet.data.remote

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PoetApi {

    @GET("api/ganjoor/poets")
    suspend fun getPoetList() : List<PoetDto>

    @GET("api/ganjoor/poet/{poetId}")
    suspend fun getPoetDetails(
        @Path("poetId") poetId: Int,
        @Query("catPoems") catPoems: Boolean = false
    ) : PoetDetailsDto

    @GET("api/ganjoor/cat/{catId}")
    suspend fun getSubCategories(
        @Path("catId") catId: Int,
        @Query("poems") poems: Boolean = true,
        @Query("mainSections") mainSections: Boolean = false
    ) : PoetDetailsDto
}