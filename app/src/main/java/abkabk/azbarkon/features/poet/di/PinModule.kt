package abkabk.azbarkon.features.poet.di

import abkabk.azbarkon.common.AzbarkonDatabase
import abkabk.azbarkon.features.poet.data.repository.PinnedPoetRepositoryImpl
import abkabk.azbarkon.features.poet.domain.repository.PinnedPoetRepository
import abkabk.azbarkon.features.poet.domain.use_case.GetPinnedPoetListUseCase
import abkabk.azbarkon.features.poet.domain.use_case.PinPoetListUseCase
import abkabk.azbarkon.features.poet.domain.use_case.UnPinPoetListUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object PinModule {

    @Provides
    @ViewModelScoped
    fun providePinnedPoetRepository(db: AzbarkonDatabase): PinnedPoetRepository {
        return PinnedPoetRepositoryImpl(db.pinDao)
    }

    @Provides
    @ViewModelScoped
    fun provideGetPinnedPoetListUseCase(pinnedPoetRepository: PinnedPoetRepository): GetPinnedPoetListUseCase {
        return GetPinnedPoetListUseCase(pinnedPoetRepository)
    }

    @Provides
    @ViewModelScoped
    fun providePinPoetListUseCase(pinnedPoetRepository: PinnedPoetRepository): PinPoetListUseCase {
        return PinPoetListUseCase(pinnedPoetRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideUnPinPoetListUseCase(pinnedPoetRepository: PinnedPoetRepository): UnPinPoetListUseCase {
        return UnPinPoetListUseCase(pinnedPoetRepository)
    }
}