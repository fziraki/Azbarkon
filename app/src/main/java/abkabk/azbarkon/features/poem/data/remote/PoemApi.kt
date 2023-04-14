package abkabk.azbarkon.features.poem.data.remote

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PoemApi {

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