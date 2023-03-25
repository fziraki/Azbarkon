package abkabk.azbarkon.features.poet.domain.repository

import abkabk.azbarkon.features.poet.domain.model.Poet
import abkabk.azbarkon.core.Resource
import kotlinx.coroutines.flow.Flow

interface PoetRepository {
    fun getPoetList() : Flow<Resource<List<Poet>>>
}