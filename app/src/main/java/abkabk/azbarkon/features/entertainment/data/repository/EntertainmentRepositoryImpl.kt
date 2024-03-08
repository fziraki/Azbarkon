package abkabk.azbarkon.features.entertainment.data.repository

import abkabk.azbarkon.features.entertainment.data.remote.EntertainmentApi
import abkabk.azbarkon.features.entertainment.domain.PoemDetails
import abkabk.azbarkon.features.entertainment.domain.repository.EntertainmentRepository
import abkabk.azbarkon.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class EntertainmentRepositoryImpl @Inject constructor(
    private val entertainmentApi: EntertainmentApi
) : EntertainmentRepository {

    override fun getFaal(): Flow<Resource<PoemDetails>> = flow {

        emit(Resource.Loading())

        try {
            val remotePoemDetails = entertainmentApi.getFaal()
            emit(Resource.Success(remotePoemDetails.toPoemDetails()))
        }catch (e: HttpException){
            emit(Resource.Error(
                message = e.message?:"Something went wrong!",
            ))
        }catch (e: IOException){
            emit(Resource.Error(
                message = "Couldn't reach server! check your connection.",
            ))
        }

    }

}