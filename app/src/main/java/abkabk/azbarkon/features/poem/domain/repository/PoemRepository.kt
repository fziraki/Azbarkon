package abkabk.azbarkon.features.poem.domain.repository

import abkabk.azbarkon.core.Resource
import abkabk.azbarkon.features.poem.domain.PoemDetails
import abkabk.azbarkon.features.poet.domain.PoetDetails
import kotlinx.coroutines.flow.Flow

interface PoemRepository {

    fun getPoems(catId: Int) : Flow<Resource<PoetDetails>>

    fun getPoemDetails(poemId: Int) : Flow<Resource<PoemDetails>>
}