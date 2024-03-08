package abkabk.azbarkon.features.entertainment.domain.repository

import abkabk.azbarkon.features.entertainment.domain.PoemDetails
import abkabk.azbarkon.utils.Resource
import kotlinx.coroutines.flow.Flow

interface EntertainmentRepository {

    fun getFaal() : Flow<Resource<PoemDetails>>
}