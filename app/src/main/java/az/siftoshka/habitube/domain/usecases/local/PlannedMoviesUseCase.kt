package az.siftoshka.habitube.domain.usecases.local

import az.siftoshka.habitube.domain.model.Movie
import az.siftoshka.habitube.domain.repository.LocalRepository
import az.siftoshka.habitube.domain.repository.PlannedRepository
import az.siftoshka.habitube.domain.repository.RealtimeRepository
import az.siftoshka.habitube.presentation.screens.settings.sort.SortType
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * Use-case to get planned movies calls from repository.
 */
class PlannedMoviesUseCase @Inject constructor(
    private val repository: PlannedRepository,
    private val localRepository: LocalRepository,
    private val realtimeRepository: RealtimeRepository
) {

    suspend fun addMovie(movie: Movie) {
        repository.addMovie(movie)
        val user = FirebaseAuth.getInstance().currentUser
        if (user != null) {
            realtimeRepository.addMovieAsPlanned(movie, user)
        }
    }

    suspend fun deleteMovie(movie: Movie) {
        repository.deleteMovie(movie)
        val user = FirebaseAuth.getInstance().currentUser
        if (user != null) {
            realtimeRepository.deleteMovieFromPlanned(movie, user)
        }
    }

    suspend fun deleteAllMovies() {
        repository.deleteAllMovies()
        val user = FirebaseAuth.getInstance().currentUser
        if (user != null) {
            realtimeRepository.deleteAllPlannedMovies(user)
        }
    }

    suspend fun getMovie(movieId: Int): Movie {
        return repository.getMovie(movieId) ?: Movie()
    }

    suspend fun isMovieExist(movieId: Int): Boolean {
        return repository.isMovieExist(movieId)
    }

    fun getMovies(): Flow<List<Movie>> {
        return repository.getMovies().map { movies ->
            when (localRepository.getSortType()) {
                SortType.RECENTLY -> movies.sortedByDescending { it.addedDate }
                SortType.FIRST -> movies.sortedBy { it.addedDate }
                SortType.TITLE -> movies.sortedBy { it.title }
                SortType.RATING -> movies.sortedByDescending { it.myRating }
                SortType.RELEASE_DESC -> movies.sortedByDescending { it.releaseDate }
                SortType.RELEASE_ASC -> movies.sortedBy { it.releaseDate }
            }
        }
    }
}