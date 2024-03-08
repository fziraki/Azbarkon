package abkabk.azbarkon.features.entertainment.data.remote

import retrofit2.http.GET
import retrofit2.http.Headers

interface EntertainmentApi {

    @Headers("isAuthorizable: false")
    @GET("api/ganjoor/hafez/faal")
    suspend fun getFaal() : PoemDetailsDto

}