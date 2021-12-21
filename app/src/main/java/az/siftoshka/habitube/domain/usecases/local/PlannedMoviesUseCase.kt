package az.siftoshka.habitube.domain.usecases.local

import az.siftoshka.habitube.domain.model.Movie
import az.siftoshka.habitube.domain.repository.PlannedRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * Use-case tp get planned movies calls from repository.
 */
class PlannedMoviesUseCase @Inject constructor(
    private val repository: PlannedRepository
) {

    suspend fun addMovie(movie: Movie) {
        repository.addMovie(movie)
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