package abkabk.azbarkon.features.entertainment.di

import abkabk.azbarkon.features.entertainment.data.remote.EntertainmentApi
import abkabk.azbarkon.features.entertainment.data.repository.EntertainmentRepositoryImpl
import abkabk.azbarkon.features.entertainment.domain.repository.EntertainmentRepository
import abkabk.azbarkon.features.entertainment.domain.use_case.GetFaalUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import retrofit2.Retrofit

@Module
@InstallIn(ViewModelComponent::class)
object EntertainmentModule {

    @Provides
    @ViewModelScoped
    fun provideGetFaalUseCase(entertainmentRepository: EntertainmentRepository): GetFaalUseCase {
        return GetFaalUseCase(entertainmentRepository)
    }


    @Provides
    @ViewModelScoped
    fun provideEntertainmentRepository(entertainmentApi: EntertainmentApi): EntertainmentRepository {
        return EntertainmentRepositoryImpl(entertainmentApi)
    }

    @Provides
    @ViewModelScoped
    fun provideEntertainmentAPi(retrofit: Retrofit): EntertainmentApi {
        return retrofit.create(EntertainmentApi::class.java)
    }
}