package az.siftoshka.habitube.domain.repository

import az.siftoshka.habitube.domain.model.Movie
import az.siftoshka.habitube.domain.model.TvShow
import com.google.firebase.auth.FirebaseUser

/**
 * The repository interface for Firebase Realtime Database.
 */
interface RealtimeRepository {

    fun addMovieAsWatched(movie: Movie, user: FirebaseUser)

    fun addMovieAsPlanned(movie: Movie, user: FirebaseUser)

    fun addShowAsWatched(show: TvShow, user: FirebaseUser)

    fun addShowAsPlanned(show: TvShow, user: FirebaseUser)

    fun deleteMovieFromWatched(movie: Movie, user: FirebaseUser)

    fun deleteMovieFromPlanned(movie: Movie, user: FirebaseUser)

    fun deleteShowFromWatched(show: TvShow, user: FirebaseUser)

    fun deleteShowFromPlanned(show: TvShow, user: FirebaseUser)

    fun deleteAllWatchedMovies(user: FirebaseUser)

    fun deleteAllPlannedMovies(user: FirebaseUser)

    fun deleteAllWatchedShows(user: FirebaseUser)

    fun deleteAllPlannedShows(user: FirebaseUser)
}