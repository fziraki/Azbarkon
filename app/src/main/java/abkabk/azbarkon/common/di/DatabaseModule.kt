package abkabk.azbarkon.common.di

import abkabk.azbarkon.common.AzbarkonDatabase
import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAzbarkonDatabase(app: Application): AzbarkonDatabase {
        return Room.databaseBuilder(
            app, AzbarkonDatabase::class.java, "azbarkon_db"
        )
        .build()
    }
}