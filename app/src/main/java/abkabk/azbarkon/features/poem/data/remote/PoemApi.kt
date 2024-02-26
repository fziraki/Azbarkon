package abkabk.azbarkon.features.poem.data.remote

import abkabk.azbarkon.features.poet.data.remote.PoetDetailsDto
import abkabk.azbarkon.utils.CacheConfigHeaderInterceptor.Companion.HEADER_CACHE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface PoemApi {

    @Headers("isAuthorizable: false", "${HEADER_CACHE}: 60 * 60 * 24")
    @GET("api/ganjoor/cat/{catId}")
    suspend fun getPoems(
        @Path("catId") catId: Int,
        @Query("poems") poems: Boolean = true,
        @Query("mainSections") mainSections: Boolean = false
    ) : PoetDetailsDto

    @Headers("isAuthorizable: false", "${HEADER_CACHE}: 60 * 60 * 24")
    @GET("api/ganjoor/poem/{poemId}")
    suspend fun getPoemDetails(
        @Path("poemId") poemId: Int,
        @Query("catInfo") catInfo: Boolean = false,
        @Query("catPoems") catPoems: Boolean = false,
        @Query("rhymes") rhymes: Boolean = false,
        @Query("recitations") recitations: Boolean = false,
        @Query("images") images: Boolean = false,
        @Query("songs") songs: Boolean = false,
        @Query("comments") comments: Boolean = false,
        @Query("verseDetails") verseDetails: Boolean = false,
        @Query("navigation") navigation: Boolean = false,
        @Query("relatedpoems") relatedpoems: Boolean = false
        ) : PoemDetailsDto

}