package az.siftoshka.habitube.data.repository

import az.siftoshka.habitube.data.model.MovieDAO
import az.siftoshka.habitube.data.model.ShowDAO
import az.siftoshka.habitube.domain.model.Movie
import az.siftoshka.habitube.domain.model.TvShow
import az.siftoshka.habitube.domain.repository.WatchedRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

/**
 * The implementation of repository class for Watched movies & tv shows.
 */
class WatchedRepositoryImpl(
    private val movieDAO: MovieDAO,
    private val showDAO: ShowDAO
) : WatchedRepository {

    override suspend fun addMovie(movie: Movie) = withContext(Dispatchers.IO) {
        movieDAO.addMovie(movie)
    }

    override fun getMovies(): Flow<List<Movie>> {
        return movieDAO.getMovies()
    }

    override suspend fun getMovie(movieId: Int): Movie? {
        return movieDAO.getMovie(movieId)
    }

    override suspend fun getMovieRating(movieId: Int): Float? {
        return movieDAO.getMovieRating(movieId)
    }

    override suspend fun isMovieExist(movieId: Int): Boolean {
        return movieDAO.getMovieCount(movieId) > 0
    }

    override suspend fun deleteMovie(movie: Movie) = withContext(Dispatchers.IO) {
        movieDAO.deleteMovie(movie)
    }

    override suspend fun deleteAllMovies() = withContext(Dispatchers.IO) {
        movieDAO.deleteAllMovies()
    }

    override suspend fun addShow(show: TvShow) = withContext(Dispatchers.IO) {
        showDAO.addShow(show)
    }

    override fun getShows(): Flow<List<TvShow>> {
        return showDAO.getShows()
    }

    override suspend fun getShowRating(showId: Int): Float? {
        return showDAO.getShowRating(showId)
    }

    override suspend fun isShowExist(showId: Int): Boolean {
        return showDAO.getShowCount(showId) > 0
    }

    override suspend fun getShow(showId: Int): TvShow? {
        return showDAO.getShow(showId)
    }

    override suspend fun deleteShow(show: TvShow) = withContext(Dispatchers.IO) {
        showDAO.deleteShow(show)
    }

    override suspend fun deleteAllShows() = withContext(Dispatchers.IO) {
        showDAO.deleteAllShows()
    }
}