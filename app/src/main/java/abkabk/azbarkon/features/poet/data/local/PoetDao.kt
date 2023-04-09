package abkabk.azbarkon.features.poet.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface PoetDao {

    @Query("SELECT * FROM PoetEntity")
    suspend fun getPoets(): List<PoetEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPoets(poets: List<PoetEntity>)


}