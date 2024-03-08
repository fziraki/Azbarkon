package abkabk.azbarkon.features.entertainment.domain.use_case

import abkabk.azbarkon.features.entertainment.domain.PoemDetails
import abkabk.azbarkon.features.entertainment.domain.repository.EntertainmentRepository
import abkabk.azbarkon.utils.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFaalUseCase @Inject constructor(
    private val entertainmentRepository: EntertainmentRepository
) {
    operator fun invoke(): Flow<Resource<PoemDetails>> {
        return entertainmentRepository.getFaal()
    }
}