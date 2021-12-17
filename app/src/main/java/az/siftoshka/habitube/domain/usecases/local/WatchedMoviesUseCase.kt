package az.siftoshka.habitube.domain.usecases.local

import az.siftoshka.habitube.domain.model.Movie
import az.siftoshka.habitube.domain.repository.PlannedRepository
import az.siftoshka.habitube.domain.repository.WatchedRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * Use-case tp get watched movies calls from repository.
 */
class WatchedMoviesUseCase @Inject constructor(
    private val repository: WatchedRepository
) {

    suspend fun addMovie(movie: Movie) {
        repository.addMovie(movie)
    }

    suspend fun updateMovie(movie: Movie) {
        repository.updateMovie(movie)
    }

    suspend fun deleteMovie(movie: Movie) {
        repository.deleteMovie(movie)
    }

    suspend fun deleteAllMovies() {
        repository.deleteAllMovies()
    }

    suspend fun getMovie(movieId: Int): Movie {
        return repository.getMovie(movieId) ?: Movie()
    }

    fun getMovies(): Flow<List<Movie>> {
        return repository.getMovies().map { movies ->
            movies.sortedBy { it.title }
        }
    }
}