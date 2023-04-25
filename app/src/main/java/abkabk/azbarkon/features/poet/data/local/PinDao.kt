package abkabk.azbarkon.features.poet.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface PinDao {

    @Query("SELECT * FROM PinEntity")
    suspend fun getPinnedPoets(): List<PinEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPinnedPoets(poetsIds: List<PinEntity>): List<Long>

    @Query("DELETE FROM PinEntity WHERE id IN (:poetsIds)")
    suspend fun removePinnedPoets(poetsIds: List<Int>)
}