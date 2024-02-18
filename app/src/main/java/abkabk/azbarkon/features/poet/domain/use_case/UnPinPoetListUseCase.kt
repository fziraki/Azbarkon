package abkabk.azbarkon.features.poet.domain.use_case

import abkabk.azbarkon.features.poet.domain.repository.PinnedPoetRepository
import abkabk.azbarkon.utils.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UnPinPoetListUseCase @Inject constructor(
    private val pinnedPoetRepository: PinnedPoetRepository
) {
    operator fun invoke(poetsIds: List<Int>): Flow<Resource<Unit>> {
        return pinnedPoetRepository.removePinnedPoets(poetsIds)
    }
}