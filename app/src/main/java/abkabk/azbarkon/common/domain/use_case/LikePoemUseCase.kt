package abkabk.azbarkon.common.domain.use_case

import abkabk.azbarkon.common.Resource
import abkabk.azbarkon.common.domain.repository.LikedPoemRepository
import abkabk.azbarkon.features.poem.domain.PoemDetails
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LikePoemUseCase @Inject constructor(
    private val likedPoemRepository: LikedPoemRepository
) {
    operator fun invoke(poem: PoemDetails): Flow<Resource<Long>> {
        return likedPoemRepository.insertLikedPoem(poem)
    }
}