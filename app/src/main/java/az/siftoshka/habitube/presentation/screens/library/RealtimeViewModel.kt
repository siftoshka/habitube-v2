package az.siftoshka.habitube.presentation.screens.library

import androidx.lifecycle.ViewModel
import az.siftoshka.habitube.domain.model.FirebaseMedia
import az.siftoshka.habitube.domain.model.FirebaseShowMedia
import az.siftoshka.habitube.domain.usecases.local.PlannedMoviesUseCase
import az.siftoshka.habitube.domain.usecases.local.PlannedTvShowUseCase
import az.siftoshka.habitube.domain.usecases.local.WatchedMoviesUseCase
import az.siftoshka.habitube.domain.usecases.local.WatchedTvShowUseCase
import az.siftoshka.habitube.domain.usecases.remote.GetRemoteMovieUseCase
import az.siftoshka.habitube.domain.usecases.remote.GetRemoteShowUseCase
import az.siftoshka.habitube.domain.usecases.remote.RealtimeUseCase
import az.siftoshka.habitube.domain.util.Constants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

/**
 * [ViewModel] for Firebase Realtime Database.
 */
@HiltViewModel
class RealtimeViewModel @Inject constructor(
    private val getMovieUseCase: GetRemoteMovieUseCase,
    private val getShowUseCase: GetRemoteShowUseCase,
    private val watchedMoviesUseCase: WatchedMoviesUseCase,
    private val plannedMoviesUseCase: PlannedMoviesUseCase,
    private val watchedTvShowUseCase: WatchedTvShowUseCase,
    private val plannedTvShowUseCase: PlannedTvShowUseCase,
    private val realtimeUseCase: RealtimeUseCase
) : ViewModel() {

    private val coroutineScope = CoroutineScope(Job() + Dispatchers.Default)

    fun syncData() {
        val user = FirebaseAuth.getInstance().currentUser
        if (user != null) {
            coroutineScope.launch {
                checkWatchedMovies(user)
                checkWatchedShows(user)
                checkPlannedMovies(user)
                checkPlannedShows(user)
            }

            coroutineScope.launch {
                uploadWatchedMovies()
                uploadPlannedMovies()
                uploadWatchedShows()
                uploadPlannedShows()
            }
        }
    }

    private suspend fun getWatchedMovie(movieId: Int, rate: Float?) {
        getMovieUseCase(movieId).let { movie ->
            watchedMoviesUseCase.addMovie(movie, rate)
        }
    }

    private suspend fun getPlannedMovie(movieId: Int) {
        getMovieUseCase(movieId).let { movie ->
            plannedMoviesUseCase.addMovie(movie)
        }
    }

    private suspend fun getWatchedShow(showId: Int, rate: Float?) {
        getShowUseCase(showId).let { show ->
            watchedTvShowUseCase.addShow(show, rate)
        }
    }

    private suspend fun getPlannedShow(showId: Int) {
        getShowUseCase(showId).let { show ->
            plannedTvShowUseCase.addShow(show)
        }
    }

    private fun uploadWatchedMovies() {
        watchedMoviesUseCase.getMovies().flowOn(Dispatchers.Unconfined).onEach { result ->
            for (movie in result) {
                realtimeUseCase.addMovieAsWatched(movie)
            }
        }.launchIn(coroutineScope)
    }

    private fun uploadPlannedMovies() {
        plannedMoviesUseCase.getMovies().flowOn(Dispatchers.Unconfined).onEach { result ->
            for (movie in result) {
                realtimeUseCase.addMovieAsPlanned(movie)
            }
        }.launchIn(coroutineScope)
    }

    private fun uploadWatchedShows() {
        watchedTvShowUseCase.getShows().flowOn(Dispatchers.Unconfined).onEach { result ->
            for (show in result) {
                realtimeUseCase.addShowAsWatched(show)
            }
        }.launchIn(coroutineScope)
    }

    private fun uploadPlannedShows() {
        plannedTvShowUseCase.getShows().flowOn(Dispatchers.Unconfined).onEach { result ->
            for (show in result) {
                realtimeUseCase.addShowAsPlanned(show)
            }
        }.launchIn(coroutineScope)
    }


    private fun checkWatchedMovies(user: FirebaseUser) {
        val databaseRef = FirebaseDatabase.getInstance().reference.child(Constants.WATCHED_MOVIE).child(user.uid)
        databaseRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (postSnapshot in snapshot.children) {
                    val mediaId = postSnapshot.getValue(FirebaseMedia::class.java)?.id
                    coroutineScope.launch {
                        if (!watchedMoviesUseCase.isMovieExist(mediaId ?: 0)) {
                            launch {
                                getWatchedMovie(movieId = mediaId ?: 0, postSnapshot.getValue(FirebaseMedia::class.java)?.rate)
                            }
                        }
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }

    private fun checkPlannedMovies(user: FirebaseUser) {
        val databaseRef = FirebaseDatabase.getInstance().reference.child(Constants.PLANNING_MOVIE).child(user.uid)
        databaseRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (postSnapshot in snapshot.children) {
                    val mediaId = postSnapshot.getValue(FirebaseMedia::class.java)?.id
                    coroutineScope.launch {
                        if (!plannedMoviesUseCase.isMovieExist(mediaId ?: 0)) {
                            launch {
                                getPlannedMovie(movieId = mediaId ?: 0)
                            }
                        }
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }

    private fun checkWatchedShows(user: FirebaseUser) {
        val databaseRef = FirebaseDatabase.getInstance().reference.child(Constants.WATCHED_SHOW).child(user.uid)
        databaseRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (postSnapshot in snapshot.children) {
                    val mediaId = postSnapshot.getValue(FirebaseShowMedia::class.java)?.id
                    coroutineScope.launch {
                        if (!watchedTvShowUseCase.isShowExist(mediaId ?: 0)) {
                            async {
                                getWatchedShow(showId = mediaId ?: 0, postSnapshot.getValue(FirebaseShowMedia::class.java)?.rate)
                            }
                        }
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }

    private fun checkPlannedShows(user: FirebaseUser) {
        val databaseRef = FirebaseDatabase.getInstance().reference.child(Constants.PLANNING_SHOW).child(user.uid)
        databaseRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (postSnapshot in snapshot.children) {
                    val mediaId = postSnapshot.getValue(FirebaseShowMedia::class.java)?.id
                    coroutineScope.launch {
                        if (!plannedTvShowUseCase.isShowExist(mediaId ?: 0)) {
                            async {
                                getPlannedShow(showId = mediaId ?: 0)
                            }
                        }
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }
}