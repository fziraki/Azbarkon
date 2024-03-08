package abkabk.azbarkon.common.domain.repository

import abkabk.azbarkon.features.entertainment.domain.PoemDetails
import abkabk.azbarkon.utils.Resource
import kotlinx.coroutines.flow.Flow

interface LikedPoemRepository {
    fun getLikedPoemList() : Flow<Resource<List<PoemDetails>>>
    fun insertLikedPoem(poem: PoemDetails): Flow<Resource<Long>>
    fun removeLikedPoems(poemId: Int): Flow<Resource<Unit>>
}