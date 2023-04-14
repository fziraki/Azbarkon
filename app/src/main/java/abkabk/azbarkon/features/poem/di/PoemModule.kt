package abkabk.azbarkon.features.poem.di

import abkabk.azbarkon.features.poem.data.remote.PoemApi
import abkabk.azbarkon.features.poem.data.repository.PoemRepositoryImpl
import abkabk.azbarkon.features.poem.domain.repository.PoemRepository
import abkabk.azbarkon.features.poem.domain.use_case.GetPoemDetailsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import retrofit2.Retrofit

@Module
@InstallIn(ViewModelComponent::class)
object PoemModule {

    @Provides
    @ViewModelScoped
    fun provideGetPoemDetailsUseCase(poemRepository: PoemRepository): GetPoemDetailsUseCase {
        return GetPoemDetailsUseCase(poemRepository)
    }


    @Provides
    @ViewModelScoped
    fun providePoemRepository(poemApi: PoemApi): PoemRepository{
        return PoemRepositoryImpl(poemApi)
    }

    @Provides
    @ViewModelScoped
    fun providePoemAPi(retrofit: Retrofit): PoemApi{
        return retrofit.create(PoemApi::class.java)
    }
}