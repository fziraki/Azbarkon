package abkabk.azbarkon.common.di

import abkabk.azbarkon.common.AzbarkonDatabase
import abkabk.azbarkon.common.data.repository.LikedPoemRepositoryImpl
import abkabk.azbarkon.common.domain.repository.LikedPoemRepository
import abkabk.azbarkon.common.domain.use_case.GetLikedPoemListUseCase
import abkabk.azbarkon.common.domain.use_case.LikePoemUseCase
import abkabk.azbarkon.common.domain.use_case.UnLikePoemUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object LikeModule {

    @Provides
    @ViewModelScoped
    fun provideLikedPoemRepository(db: AzbarkonDatabase): LikedPoemRepository {
        return LikedPoemRepositoryImpl(db.likeDao)
    }

    @Provides
    @ViewModelScoped
    fun provideGetLikedPoemListUseCase(likedPoemRepository: LikedPoemRepository): GetLikedPoemListUseCase {
        return GetLikedPoemListUseCase(likedPoemRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideLikePoemUseCase(likedPoemRepository: LikedPoemRepository): LikePoemUseCase {
        return LikePoemUseCase(likedPoemRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideUnLikePoemUseCase(likedPoemRepository: LikedPoemRepository): UnLikePoemUseCase {
        return UnLikePoemUseCase(likedPoemRepository)
    }
}