package az.siftoshka.habitube.domain.repository

import az.siftoshka.habitube.domain.model.Movie
import az.siftoshka.habitube.domain.model.TvShow
import kotlinx.coroutines.flow.Flow

/**
 * The repository interface for Planning movies & tv shows.
 */
interface PlannedRepository {

    suspend fun addMovie(movie: Movie)

    suspend fun updateMovie(movie: Movie)

    fun getMovies() : Flow<List<Movie>>

    suspend fun getMovie(movieId: Int) : Movie?

    suspend fun deleteMovie(movie: Movie)

    suspend fun deleteAllMovies()

    suspend fun addShow(show: TvShow)

    suspend fun updateShow(show: TvShow)

    fun getShows() : Flow<List<TvShow>>

    suspend fun getShow(showId: Int) : TvShow?

    suspend fun deleteShow(show: TvShow)

    suspend fun deleteAllShows()
}