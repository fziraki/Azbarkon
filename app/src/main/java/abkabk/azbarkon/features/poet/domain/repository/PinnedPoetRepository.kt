package abkabk.azbarkon.features.poet.domain.repository

import abkabk.azbarkon.common.Resource
import kotlinx.coroutines.flow.Flow

interface PinnedPoetRepository {
    fun getPinnedPoetList() : Flow<Resource<List<Int>>>
    fun insertPinnedPoets(poetsIds: List<Int>): Flow<Resource<List<Long>>>
    fun removePinnedPoets(poetsIds: List<Int>): Flow<Resource<Unit>>
}