package abkabk.azbarkon.features.poet.domain.use_case

import abkabk.azbarkon.common.Resource
import abkabk.azbarkon.features.poet.domain.repository.PinnedPoetRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPinnedPoetListUseCase @Inject constructor(
    private val pinnedPoetRepository: PinnedPoetRepository
) {
    operator fun invoke(): Flow<Resource<List<Int>>> {
        return pinnedPoetRepository.getPinnedPoetList()
    }
}