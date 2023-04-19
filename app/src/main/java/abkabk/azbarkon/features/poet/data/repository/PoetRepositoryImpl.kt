package abkabk.azbarkon.features.poet.data.repository

import abkabk.azbarkon.core.Resource
import abkabk.azbarkon.features.poet.data.local.PoetDao
import abkabk.azbarkon.features.poet.data.remote.PoetApi
import abkabk.azbarkon.features.poet.domain.Poet
import abkabk.azbarkon.features.poet.domain.PoetDetails
import abkabk.azbarkon.features.poet.domain.repository.PoetRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class PoetRepositoryImpl @Inject constructor(
    private val poetApi: PoetApi,
    private val poetDao: PoetDao
) : PoetRepository {

    override fun getPoetList(): Flow<Resource<List<Poet>>> = flow {
        emit(Resource.Loading())

        val cachedPoets = poetDao.getPoets().map { it.toPoet() }
        emit(Resource.Loading(data = cachedPoets))

        try {
            val remotePoetList = poetApi.getPoetList()
            poetDao.insertPoets(remotePoetList.map { it.toPoetEntity() })
        }catch (e: HttpException){
            emit(Resource.Error(
                message = e.message?:"Something went wrong!",
                data = cachedPoets
            ))
        }catch (e: IOException){
            emit(Resource.Error(
                message = "Couldn't reach server! check your connection.",
                data = cachedPoets
            ))
        }

        val newCachedPoets = poetDao.getPoets().map { it.toPoet() }
        emit(Resource.Success(newCachedPoets))
    }

    override fun getSubCategories(catId: Int): Flow<Resource<PoetDetails>> = flow {

        emit(Resource.Loading())

        try {
            val remoteSubCategories = poetApi.getSubCategories(catId)
            emit(Resource.Success(remoteSubCategories.toPoetDetails()))
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