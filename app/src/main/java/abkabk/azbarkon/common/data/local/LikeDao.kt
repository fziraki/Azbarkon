package abkabk.azbarkon.common.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface LikeDao {

    @Query("SELECT * FROM LikeEntity")
    suspend fun getLikedPoems(): List<LikeEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLikedPoem(poemId: LikeEntity): Long

    @Query("DELETE FROM LikeEntity WHERE id =:poemId")
    suspend fun removeLikedPoem(poemId: Int)
}