package az.siftoshka.habitube.domain.usecases.remote

import az.siftoshka.habitube.domain.model.Movie
import az.siftoshka.habitube.domain.model.TvShow
import az.siftoshka.habitube.domain.repository.RealtimeRepository
import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject

/**
 * Use-case to upload data to Firebase Realtime Database.
 */
class RealtimeUseCase @Inject constructor(
    private val realtimeRepository: RealtimeRepository
) {

    fun addMovieAsWatched(movie: Movie) {
        val user = FirebaseAuth.getInstance().currentUser
        if (user != null) {
            realtimeRepository.addMovieAsWatched(movie, user)
        }
    }

    fun addMovieAsPlanned(movie: Movie) {
        val user = FirebaseAuth.getInstance().currentUser
        if (user != null) {
            realtimeRepository.addMovieAsPlanned(movie, user)
        }
    }

    fun addShowAsWatched(show: TvShow) {
        val user = FirebaseAuth.getInstance().currentUser
        if (user != null) {
            realtimeRepository.addShowAsWatched(show, user)
        }
    }

    fun addShowAsPlanned(show: TvShow) {
        val user = FirebaseAuth.getInstance().currentUser
        if (user != null) {
            realtimeRepository.addShowAsPlanned(show, user)
        }
    }
}