package abkabk.azbarkon.features.poet.data.remote

import retrofit2.http.GET

interface PoetApi {

    @GET("api/ganjoor/poets")
    suspend fun getPoetList() : List<PoetDto>
}