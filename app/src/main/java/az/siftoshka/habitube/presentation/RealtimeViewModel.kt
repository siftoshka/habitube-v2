package az.siftoshka.habitube.presentation

import android.content.Context
import androidx.lifecycle.ViewModel
import az.siftoshka.habitube.domain.model.FirebaseMedia
import az.siftoshka.habitube.domain.model.FirebaseShowMedia
import az.siftoshka.habitube.domain.model.Movie
import az.siftoshka.habitube.domain.model.TvShow
import az.siftoshka.habitube.domain.usecases.local.PlannedMoviesUseCase
import az.siftoshka.habitube.domain.usecases.local.PlannedTvShowUseCase
import az.siftoshka.habitube.domain.usecases.local.WatchedMoviesUseCase
import az.siftoshka.habitube.domain.usecases.local.WatchedTvShowUseCase
import az.siftoshka.habitube.domain.usecases.remote.GetMovieUseCase
import az.siftoshka.habitube.domain.usecases.remote.GetTvShowUseCase
import az.siftoshka.habitube.domain.usecases.remote.RealtimeUseCase
import az.siftoshka.habitube.domain.util.Constants
import az.siftoshka.habitube.domain.util.Resource
import az.siftoshka.habitube.domain.util.saveToStorage
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Shared [ViewModel] for Firebase Realtime Database.
 */
@HiltViewModel
class RealtimeViewModel @Inject constructor(
    private val context: Context,
    private val getMovieUseCase: GetMovieUseCase,
    private val getTvShowUseCase: GetTvShowUseCase,
    private val watchedMoviesUseCase: WatchedMoviesUseCase,
    private val plannedMoviesUseCase: PlannedMoviesUseCase,
    private val watchedTvShowUseCase: WatchedTvShowUseCase,
    private val plannedTvShowUseCase: PlannedTvShowUseCase,
    private val realtimeUseCase: RealtimeUseCase
) : ViewModel() {

    private val coroutineScope = CoroutineScope(Dispatchers.Default)

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

    private fun getMovie(movieId: Int, onPerformClick: (movie: Movie?) -> Unit) {
        getMovieUseCase(movieId).flowOn(Dispatchers.IO).onEach { result ->
            when (result) {
                is Resource.Success -> { onPerformClick(result.data) }
            }
        }.launchIn(coroutineScope)
    }

    private fun getShow(showId: Int, onPerformClick: (show: TvShow?) -> Unit) {
        getTvShowUseCase(showId).flowOn(Dispatchers.IO).onEach { result ->
            when (result) {
                is Resource.Success -> { onPerformClick(result.data) }
            }
        }.launchIn(coroutineScope)
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
                            mediaId?.let {
                                getMovie(it) { movie ->
                                    movie?.let {
                                        coroutineScope.launch {
                                            watchedMoviesUseCase.addMovie(it, postSnapshot.getValue(FirebaseMedia::class.java)?.rate)
                                            context.saveToStorage(it.posterPath, isWatched = true)
                                        }
                                    }
                                }
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
                            mediaId?.let {
                                getMovie(it) { movie ->
                                    movie?.let {
                                        coroutineScope.launch {
                                            plannedMoviesUseCase.addMovie(it)
                                            context.saveToStorage(it.posterPath, isWatched = false)
                                        }
                                    }
                                }
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
                            mediaId?.let {
                                getShow(it) { show ->
                                    show?.let {
                                        coroutineScope.launch {
                                            watchedTvShowUseCase.addShow(it, postSnapshot.getValue(FirebaseShowMedia::class.java)?.rate)
                                            context.saveToStorage(it.posterPath, isWatched = true)
                                        }
                                    }
                                }
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
                            mediaId?.let {
                                getShow(it) { show ->
                                    show?.let {
                                        coroutineScope.launch {
                                            plannedTvShowUseCase.addShow(it)
                                            context.saveToStorage(it.posterPath, isWatched = false)
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }
}