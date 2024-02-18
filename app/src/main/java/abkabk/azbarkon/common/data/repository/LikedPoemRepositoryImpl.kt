package abkabk.azbarkon.common.data.repository

import abkabk.azbarkon.common.data.local.LikeDao
import abkabk.azbarkon.common.domain.repository.LikedPoemRepository
import abkabk.azbarkon.features.poem.domain.PoemDetails
import abkabk.azbarkon.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class LikedPoemRepositoryImpl @Inject constructor(
    private val likedDao: LikeDao
) : LikedPoemRepository {

    override fun getLikedPoemList(): Flow<Resource<List<PoemDetails>>> = flow {

        try {
            emit(Resource.Success(likedDao.getLikedPoems().map { it.toPoemDetails() }))
        }catch (e: IOException){
            emit(Resource.Error(message = "Couldn't reach server! check your connection."))
        }
    }

    override fun insertLikedPoem(poem: PoemDetails): Flow<Resource<Long>> = flow {

        try {
            emit(Resource.Success(likedDao.insertLikedPoem(poem.toLikeEntity())))
        }catch (e: IOException){
            emit(Resource.Error(message = "Couldn't pin."))
        }
    }

    override fun removeLikedPoems(poemId: Int): Flow<Resource<Unit>> = flow {

        try {
            emit(Resource.Success(likedDao.removeLikedPoem(poemId)))
        }catch (e: IOException){
            emit(Resource.Error(message = "Couldn't remove."))
        }
    }

}