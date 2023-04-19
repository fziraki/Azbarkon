package abkabk.azbarkon.features.poet.di

import abkabk.azbarkon.core.AzbarkonDatabase
import abkabk.azbarkon.features.poet.data.remote.PoetApi
import abkabk.azbarkon.features.poet.data.repository.PoetRepositoryImpl
import abkabk.azbarkon.features.poet.domain.repository.PoetRepository
import abkabk.azbarkon.features.poet.domain.use_case.GetPoetListUseCase
import abkabk.azbarkon.features.poet.domain.use_case.GetSubCategoriesUseCase
import abkabk.azbarkon.features.poet.poet_details.memento.Editor
import abkabk.azbarkon.features.poet.poet_details.memento.History
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import retrofit2.Retrofit

@Module
@InstallIn(ViewModelComponent::class)
object PoetModule {

    @Provides
    @ViewModelScoped
    fun provideEditor(): Editor {
        return Editor()
    }

    @Provides
    @ViewModelScoped
    fun provideHistory(): History {
        return History()
    }

    @Provides
    @ViewModelScoped
    fun provideGetSubCategoriesUseCase(poetRepository: PoetRepository): GetSubCategoriesUseCase {
        return GetSubCategoriesUseCase(poetRepository)
    }


    @Provides
    @ViewModelScoped
    fun provideGetPoetListUseCase(poetRepository: PoetRepository): GetPoetListUseCase {
        return GetPoetListUseCase(poetRepository)
    }

    @Provides
    @ViewModelScoped
    fun providePoetRepository(poetApi: PoetApi, db: AzbarkonDatabase): PoetRepository{
        return PoetRepositoryImpl(poetApi, db.poetDao)
    }

    @Provides
    @ViewModelScoped
    fun providePoetAPi(retrofit: Retrofit): PoetApi{
        return retrofit.create(PoetApi::class.java)
    }
}