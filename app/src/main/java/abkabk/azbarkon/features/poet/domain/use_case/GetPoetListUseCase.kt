package abkabk.azbarkon.features.poet.domain.use_case

import abkabk.azbarkon.common.Resource
import abkabk.azbarkon.features.poet.domain.Poet
import abkabk.azbarkon.features.poet.domain.repository.PoetRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPoetListUseCase @Inject constructor(
    private val poetRepository: PoetRepository
) {
    operator fun invoke(): Flow<Resource<List<Poet>>> {
        return poetRepository.getPoetList()
    }
}