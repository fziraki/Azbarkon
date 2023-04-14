package abkabk.azbarkon.features.poem.domain.repository

import abkabk.azbarkon.core.Resource
import abkabk.azbarkon.features.poem.domain.PoemDetails
import kotlinx.coroutines.flow.Flow

interface PoemRepository {

    fun getPoemDetails(poemId: Int) : Flow<Resource<PoemDetails>>
}