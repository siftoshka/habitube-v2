package az.siftoshka.habitube.data.repository

import az.siftoshka.habitube.domain.model.FirebaseMedia
import az.siftoshka.habitube.domain.model.FirebaseShowMedia
import az.siftoshka.habitube.domain.model.Movie
import az.siftoshka.habitube.domain.model.TvShow
import az.siftoshka.habitube.domain.repository.RealtimeRepository
import az.siftoshka.habitube.domain.util.Constants
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase
import javax.inject.Inject

/**
 * The implementation of repository interface for Firebase Realtime Database.
 */
class RealtimeRepositoryImpl @Inject constructor() : RealtimeRepository {

    override fun addMovieAsWatched(movie: Movie, user: FirebaseUser) {
        val databaseRef = FirebaseDatabase.getInstance().reference.child(Constants.WATCHED_MOVIE).child(user.uid)
        databaseRef.child(movie.id.toString()).setValue(FirebaseMedia(movie.id, movie.myRating))
    }

    override fun addMovieAsPlanned(movie: Movie, user: FirebaseUser) {
        val databaseRef = FirebaseDatabase.getInstance().reference.child(Constants.PLANNING_MOVIE).child(user.uid)
        databaseRef.child(movie.id.toString()).setValue(FirebaseMedia(movie.id, movie.myRating))
    }

    override fun addShowAsWatched(show: TvShow, user: FirebaseUser) {
        val databaseRef = FirebaseDatabase.getInstance().reference.child(Constants.WATCHED_SHOW).child(user.uid)
        databaseRef.child(show.id.toString()).setValue(FirebaseShowMedia(show.id, show.myRating))
    }

    override fun addShowAsPlanned(show: TvShow, user: FirebaseUser) {
        val databaseRef = FirebaseDatabase.getInstance().reference.child(Constants.PLANNING_SHOW).child(user.uid)
        databaseRef.child(show.id.toString()).setValue(FirebaseShowMedia(show.id, show.myRating))
    }

    override fun deleteMovieFromWatched(movie: Movie, user: FirebaseUser) {
        val databaseRef = FirebaseDatabase.getInstance().reference.child(Constants.WATCHED_MOVIE).child(user.uid)
        databaseRef.child(movie.id.toString()).removeValue()
    }

    override fun deleteMovieFromPlanned(movie: Movie, user: FirebaseUser) {
        val databaseRef = FirebaseDatabase.getInstance().reference.child(Constants.PLANNING_MOVIE).child(user.uid)
        databaseRef.child(movie.id.toString()).removeValue()
    }

    override fun deleteShowFromWatched(show: TvShow, user: FirebaseUser) {
        val databaseRef = FirebaseDatabase.getInstance().reference.child(Constants.WATCHED_SHOW).child(user.uid)
        databaseRef.child(show.id.toString()).removeValue()
    }

    override fun deleteShowFromPlanned(show: TvShow, user: FirebaseUser) {
        val databaseRef = FirebaseDatabase.getInstance().reference.child(Constants.PLANNING_SHOW).child(user.uid)
        databaseRef.child(show.id.toString()).removeValue()
    }

    override fun deleteAllWatchedMovies(user: FirebaseUser) {
        val databaseRef = FirebaseDatabase.getInstance().reference.child(Constants.WATCHED_MOVIE).child(user.uid)
        databaseRef.removeValue()
    }

    override fun deleteAllPlannedMovies(user: FirebaseUser) {
        val databaseRef = FirebaseDatabase.getInstance().reference.child(Constants.PLANNING_MOVIE).child(user.uid)
        databaseRef.removeValue()
    }

    override fun deleteAllWatchedShows(user: FirebaseUser) {
        val databaseRef = FirebaseDatabase.getInstance().reference.child(Constants.WATCHED_SHOW).child(user.uid)
        databaseRef.removeValue()
    }

    override fun deleteAllPlannedShows(user: FirebaseUser) {
        val databaseRef = FirebaseDatabase.getInstance().reference.child(Constants.PLANNING_SHOW).child(user.uid)
        databaseRef.removeValue()
    }

}