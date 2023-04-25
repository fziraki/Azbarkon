package abkabk.azbarkon.features.poem.domain.use_case

import abkabk.azbarkon.common.Resource
import abkabk.azbarkon.features.poem.domain.repository.PoemRepository
import abkabk.azbarkon.features.poet.domain.PoetDetails
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPoemsUseCase @Inject constructor(
    private val poemRepository: PoemRepository
) {
    operator fun invoke(catId: Int): Flow<Resource<PoetDetails>> {
        return poemRepository.getPoems(catId)
    }
}