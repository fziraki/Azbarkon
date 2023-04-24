package abkabk.azbarkon.common

import abkabk.azbarkon.features.poet.data.local.PinDao
import abkabk.azbarkon.features.poet.data.local.PinEntity
import abkabk.azbarkon.features.poet.data.local.PoetDao
import abkabk.azbarkon.features.poet.data.local.PoetEntity
import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [PoetEntity::class, PinEntity::class],
    version = 1
)
abstract class AzbarkonDatabase: RoomDatabase() {

    abstract val poetDao: PoetDao

    abstract val pinDao: PinDao
}