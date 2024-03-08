package abkabk.azbarkon.common.domain.use_case

import abkabk.azbarkon.common.domain.repository.LikedPoemRepository
import abkabk.azbarkon.features.entertainment.domain.PoemDetails
import abkabk.azbarkon.utils.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetLikedPoemListUseCase @Inject constructor(
    private val likedPoemRepository: LikedPoemRepository
) {
    operator fun invoke(): Flow<Resource<List<PoemDetails>>> {
        return likedPoemRepository.getLikedPoemList()
    }
}