package az.siftoshka.habitube.di

import az.siftoshka.habitube.data.remote.HttpInterceptor
import az.siftoshka.habitube.data.remote.MovieService
import az.siftoshka.habitube.data.repository.RemoteRepositoryImpl
import az.siftoshka.habitube.domain.repository.RemoteRepository
import az.siftoshka.habitube.domain.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * The DI Module of the entire application.
 */
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        val interceptor = HttpInterceptor()
        val httpInterceptor = HttpLoggingInterceptor()
        return OkHttpClient
            .Builder()
            .addInterceptor(interceptor)
            .addInterceptor(httpInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC))
            .build()
    }

    @Singleton
    @Provides
    fun provideBaseUrl(): String {
        return Constants.API_URL
    }

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient, baseUrl:String): Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(baseUrl)
        .client(okHttpClient)
        .build()

    @Singleton
    @Provides
    fun provideMovieService(retrofit: Retrofit) = retrofit.create(MovieService::class.java)

    @Provides
    @Singleton
    fun provideRemoteRepository(service: MovieService): RemoteRepository {
        return RemoteRepositoryImpl(service)
    }
}