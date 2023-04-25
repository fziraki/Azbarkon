package abkabk.azbarkon.features.poet.data.repository

import abkabk.azbarkon.common.Resource
import abkabk.azbarkon.features.poet.data.local.PinDao
import abkabk.azbarkon.features.poet.data.local.PinEntity
import abkabk.azbarkon.features.poet.domain.repository.PinnedPoetRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class PinnedPoetRepositoryImpl @Inject constructor(
    private val pinDao: PinDao
) : PinnedPoetRepository {

    override fun getPinnedPoetList(): Flow<Resource<List<Int>>> = flow {

        try {
            emit(Resource.Success(pinDao.getPinnedPoets().map { it.id }))
        }catch (e: IOException){
            emit(Resource.Error(message = "Couldn't reach server! check your connection."))
        }
    }

    override fun insertPinnedPoets(poetsIds: List<Int>): Flow<Resource<List<Long>>> = flow {

        try {
            emit(Resource.Success(pinDao.insertPinnedPoets(poetsIds.map { PinEntity(id = it) })))
        }catch (e: IOException){
            emit(Resource.Error(message = "Couldn't pin."))
        }
    }

    override fun removePinnedPoets(poetsIds: List<Int>): Flow<Resource<Unit>> = flow {

        try {
            emit(Resource.Success(pinDao.removePinnedPoets(poetsIds)))
        }catch (e: IOException){
            emit(Resource.Error(message = "Couldn't remove."))
        }
    }

}