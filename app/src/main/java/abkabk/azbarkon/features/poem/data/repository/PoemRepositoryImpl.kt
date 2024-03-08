package abkabk.azbarkon.features.poem.data.repository

import abkabk.azbarkon.features.poem.data.remote.PoemApi
import abkabk.azbarkon.features.poem.domain.PoemDetails
import abkabk.azbarkon.features.poem.domain.repository.PoemRepository
import abkabk.azbarkon.features.poet.domain.PoetDetails
import abkabk.azbarkon.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class PoemRepositoryImpl @Inject constructor(
    private val poemApi: PoemApi
) : PoemRepository {

    override fun getPoems(catId: Int): Flow<Resource<PoetDetails>> = flow {

        emit(Resource.Loading())

        try {
            val remotePoems = poemApi.getPoems(catId)

            emit(Resource.Success(remotePoems.toPoetDetails()))
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


    override fun getPoemDetails(poemId: Int): Flow<Resource<PoemDetails>> = flow {

        emit(Resource.Loading())

        try {
            val remotePoemDetails = poemApi.getPoemDetails(poemId)
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