package abkabk.azbarkon.features.poem.domain.use_case

import abkabk.azbarkon.features.poem.domain.PoemDetails
import abkabk.azbarkon.features.poem.domain.repository.PoemRepository
import abkabk.azbarkon.utils.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPoemDetailsUseCase @Inject constructor(
    private val poemRepository: PoemRepository
) {
    operator fun invoke(poemId: Int): Flow<Resource<PoemDetails>> {
        return poemRepository.getPoemDetails(poemId)
    }
}