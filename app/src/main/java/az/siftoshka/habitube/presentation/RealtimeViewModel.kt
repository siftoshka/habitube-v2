package az.siftoshka.habitube.presentation

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import az.siftoshka.habitube.domain.model.FirebaseMedia
import az.siftoshka.habitube.domain.model.Movie
import az.siftoshka.habitube.domain.model.TvShow
import az.siftoshka.habitube.domain.usecases.local.WatchedMoviesUseCase
import az.siftoshka.habitube.domain.usecases.remote.GetMovieUseCase
import az.siftoshka.habitube.domain.usecases.remote.GetTvShowUseCase
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
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
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
) : ViewModel() {

    fun syncData() {
        val user = FirebaseAuth.getInstance().currentUser
        if (user != null) {
            checkWatchedMovies(user)
        }
    }

    private suspend fun getMovie(movieId: Int, onPerformClick: (movie: Movie?) -> Unit) = withContext(Dispatchers.IO) {
        getMovieUseCase(movieId).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    onPerformClick(result.data)
                }
            }
        }.launchIn(viewModelScope)
    }

    private suspend fun getShow(showId: Int, onPerformClick: (show: TvShow?) -> Unit) = withContext(Dispatchers.IO) {
        getTvShowUseCase(showId).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    onPerformClick(result.data)
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun checkWatchedMovies(user: FirebaseUser) {
        val databaseRef = FirebaseDatabase.getInstance().reference.child(Constants.WATCHED_MOVIE).child(user.uid)
        databaseRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (postSnapshot in snapshot.children) {
                    val mediaId = postSnapshot.getValue(FirebaseMedia::class.java)?.id
                    viewModelScope.launch(context = Dispatchers.IO, CoroutineStart.DEFAULT, block = {
                        if (!watchedMoviesUseCase.isMovieExist(mediaId ?: 0)) {
                            mediaId?.let {
                                getMovie(it) { movie ->
                                    movie?.let {
                                        viewModelScope.launch(context = Dispatchers.IO, CoroutineStart.DEFAULT, block = {
                                            watchedMoviesUseCase.addMovie(it, postSnapshot.getValue(FirebaseMedia::class.java)?.rate)
                                            context.saveToStorage(it.posterPath, isWatched = true)
                                        })
                                    }
                                }
                            }
                        }
                    })
                }
            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }
}