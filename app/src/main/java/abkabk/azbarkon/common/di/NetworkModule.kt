package abkabk.azbarkon.common.di

import android.content.Context
import abkabk.azbarkon.utils.AuthInterceptor
import abkabk.azbarkon.utils.CacheConfigHeaderInterceptor
import abkabk.azbarkon.utils.Constants
import abkabk.azbarkon.utils.ErrorHandler
import abkabk.azbarkon.utils.GeneralErrorHandlerImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    companion object{
        const val CACHE_DIR = "httpCache"
    }

    @Singleton
    @Provides
    fun provideErrorHandler(): ErrorHandler {
        return GeneralErrorHandlerImpl()
    }

    @Singleton
    @Provides
    fun provideConverterFactory(): Converter.Factory {
        return GsonConverterFactory.create()
    }

    @Singleton
    @Provides
    @Named("Auth")
    fun provideAuthInterceptor(): Interceptor {
        return AuthInterceptor()
    }

    @Singleton
    @Provides
    @Named("Cache")
    fun provideCacheConfigHeaderInterceptor(): Interceptor {
        return CacheConfigHeaderInterceptor()
    }


    @Provides
    @Singleton
    fun provideOkHttpClient(
        authInterceptor: AuthInterceptor,
        cacheConfigHeaderInterceptor: CacheConfigHeaderInterceptor,
        @ApplicationContext context: Context
    ): OkHttpClient {

        val httpCacheDirectory = File(context.cacheDir, CACHE_DIR)
        val cache = Cache(httpCacheDirectory, 10 * 1024 * 1024)

        return OkHttpClient.Builder()
            .cache(cache = cache)
            .connectTimeout(60L, TimeUnit.SECONDS)
            .writeTimeout(60L, TimeUnit.SECONDS)
            .readTimeout(60L, TimeUnit.SECONDS)
            .addNetworkInterceptor(
                HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }
            )
            .addInterceptor(authInterceptor)
            .addInterceptor(cacheConfigHeaderInterceptor)
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient, converterFactory: Converter.Factory): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(converterFactory)
            .build()
    }

}