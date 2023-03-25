package abkabk.azbarkon.core

import abkabk.azbarkon.features.poet.data.local.PoetDao
import abkabk.azbarkon.features.poet.data.local.PoetEntity
import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [PoetEntity::class],
    version = 1
)
abstract class AzbarkonDatabase: RoomDatabase() {

    abstract val poetDao: PoetDao
}