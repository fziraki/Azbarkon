package abkabk.azbarkon.features.search.di

import abkabk.azbarkon.features.search.data.remote.SearchApi
import abkabk.azbarkon.features.search.data.repository.SearchRepositoryImpl
import abkabk.azbarkon.features.search.domain.repository.SearchRepository
import abkabk.azbarkon.features.search.domain.use_case.SearchPoemUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import retrofit2.Retrofit

@Module
@InstallIn(ViewModelComponent::class)
object SearchModule {

    @Provides
    @ViewModelScoped
    fun provideSearchPoemUseCase(searchRepository: SearchRepository): SearchPoemUseCase {
        return SearchPoemUseCase(searchRepository)
    }


    @Provides
    @ViewModelScoped
    fun provideSearchRepository(searchApi: SearchApi): SearchRepository {
        return SearchRepositoryImpl(searchApi)
    }

    @Provides
    @ViewModelScoped
    fun provideSearchAPi(retrofit: Retrofit): SearchApi {
        return retrofit.create(SearchApi::class.java)
    }
}