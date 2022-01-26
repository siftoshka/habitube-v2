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
import az.siftoshka.habitube.data.remote.MovieApiService
import az.siftoshka.habitube.data.remote.OmdbApiService
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
import javax.inject.Named
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
    @Named("MovieDb")
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
    @Named("OMDb")
    fun provideOmdbOkHttpClient(): OkHttpClient {
        return OkHttpClient
            .Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC))
            .build()
    }

    @Singleton
    @Provides
    @Named("MovieDb")
    fun provideBaseUrl(): String = Constants.API_URL

    @Singleton
    @Provides
    @Named("OMDb")
    fun provideOmdbUrl(): String = Constants.OMDB_API_URL

    @Singleton
    @Provides
    @Named("MovieDb")
    fun provideRetrofit(@Named("MovieDb") okHttpClient: OkHttpClient, @Named("MovieDb") url: String): Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(url)
        .client(okHttpClient)
        .build()

    @Singleton
    @Provides
    @Named("OMDb")
    fun provideOmdbRetrofit(@Named("OMDb") okHttpClient: OkHttpClient, @Named("OMDb") url: String): Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(url)
        .client(okHttpClient)
        .build()

    @Singleton
    @Provides
    @Named("MovieDb")
    fun provideMovieApiService(@Named("MovieDb") retrofit: Retrofit): MovieApiService = retrofit.create(MovieApiService::class.java)

    @Singleton
    @Provides
    @Named("OMDb")
    fun provideOmdbApiService(@Named("OMDb") retrofit: Retrofit): OmdbApiService = retrofit.create(OmdbApiService::class.java)

    @Singleton
    @Provides
    fun provideMovieDBApiRepository(@Named("MovieDb") service: MovieApiService, localRepository: LocalRepository): MovieDBApiRepository {
        return MovieDBApiRepositoryImpl(service, localRepository)
    }

    @Singleton
    @Provides
    fun provideOmdbApiRepository(@Named("OMDb") service: OmdbApiService): OmdbApiRepository {
        return OmdbApiRepositoryImpl(service)
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
    fun provideLocalRepository(preferences: SharedPreferences): LocalRepository {
        return LocalRepositoryImpl(preferences)
    }

    @Provides
    @Singleton
    fun provideRealtimeRepository(): RealtimeRepository {
        return RealtimeRepositoryImpl()
    }

}