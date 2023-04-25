package abkabk.azbarkon.features.poet.domain.repository

import abkabk.azbarkon.common.Resource
import abkabk.azbarkon.features.poet.domain.Poet
import abkabk.azbarkon.features.poet.domain.PoetDetails
import kotlinx.coroutines.flow.Flow

interface PoetRepository {
    fun getPoetList() : Flow<Resource<List<Poet>>>
    fun getSubCategories(catId: Int) : Flow<Resource<PoetDetails>>
}