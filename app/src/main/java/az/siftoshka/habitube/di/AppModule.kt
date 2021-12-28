package az.siftoshka.habitube.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import az.siftoshka.habitube.data.model.PLANNED_MIGRATION_1_2
import az.siftoshka.habitube.data.model.PlannedDatabase
import az.siftoshka.habitube.data.model.WATCHED_MIGRATION_1_2
import az.siftoshka.habitube.data.model.WatchedDatabase
import az.siftoshka.habitube.data.remote.HttpInterceptor
import az.siftoshka.habitube.data.remote.MovieService
import az.siftoshka.habitube.data.repository.*
import az.siftoshka.habitube.domain.repository.*
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
    fun provideContext(application: Application): Context = application.applicationContext

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
    fun provideRetrofit(okHttpClient: OkHttpClient, baseUrl: String): Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(baseUrl)
        .client(okHttpClient)
        .build()

    @Singleton
    @Provides
    fun provideMovieService(retrofit: Retrofit): MovieService = retrofit.create(MovieService::class.java)

    @Provides
    @Singleton
    fun provideRemoteRepository(service: MovieService, localRepository: LocalRepository): RemoteRepository {
        return RemoteRepositoryImpl(service, localRepository)
    }

    @Provides
    @Singleton
    fun providePlannedDatabase(app: Application): PlannedDatabase {
        return Room.databaseBuilder(app, PlannedDatabase::class.java, Constants.PLANNED)
            .addMigrations(PLANNED_MIGRATION_1_2)
            .build()
    }

    @Provides
    @Singleton
    fun providePlannedRepository(db: PlannedDatabase): PlannedRepository {
        return PlannedRepositoryImpl(db.getMovieDAO(), db.getShowDAO())
    }

    @Provides
    @Singleton
    fun provideWatchedDatabase(app: Application): WatchedDatabase {
        return Room.databaseBuilder(app, WatchedDatabase::class.java, Constants.WATCHED)
            .addMigrations(WATCHED_MIGRATION_1_2)
            .build()
    }

    @Provides
    @Singleton
    fun provideWatchedRepository(db: WatchedDatabase): WatchedRepository {
        return WatchedRepositoryImpl(db.getMovieDAO(), db.getShowDAO())
    }

    @Provides
    @Singleton
    fun provideSharedPreferences(app: Application): SharedPreferences =
        app.applicationContext.getSharedPreferences(Constants.PREFS_NAME, Context.MODE_PRIVATE)

    @Provides
    @Singleton
    fun provideLocalRepository(app: Application, preferences: SharedPreferences): LocalRepository {
        return LocalRepositoryImpl(app, preferences)
    }

    @Provides
    @Singleton
    fun provideRealtimeRepository(): RealtimeRepository {
        return RealtimeRepositoryImpl()
    }

}