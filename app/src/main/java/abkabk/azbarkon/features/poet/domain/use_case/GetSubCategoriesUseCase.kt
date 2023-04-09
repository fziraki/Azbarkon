package abkabk.azbarkon.features.poet.domain.use_case

import abkabk.azbarkon.core.Resource
import abkabk.azbarkon.features.poet.domain.PoetDetails
import abkabk.azbarkon.features.poet.domain.repository.PoetRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSubCategoriesUseCase @Inject constructor(
    private val poetRepository: PoetRepository
) {
    operator fun invoke(catId: Int): Flow<Resource<PoetDetails>> {
        return poetRepository.getSubCategories(catId)
    }
}