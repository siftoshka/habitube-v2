package az.siftoshka.habitube.data.model

import androidx.room.*
import az.siftoshka.habitube.domain.model.Movie
import kotlinx.coroutines.flow.Flow

/**
 * The DAO interface of the [MovieDatabase].
 */
@Dao
interface MovieDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addMovie(movie: Movie)

    @Transaction @Query("SELECT * FROM movies")
    fun getMovies() : Flow<List<Movie>>

    @Transaction @Query("SELECT * FROM movies WHERE id = :movieId")
    suspend fun getMovie(movieId: Int) : Movie?

    @Transaction @Query("SELECT my_rating FROM movies WHERE id = :movieId")
    suspend fun getMovieRating(movieId: Int): Float?

    @Transaction @Query("SELECT count(*) FROM movies WHERE id = :movieId")
    suspend fun getMovieCount(movieId: Int): Int

    @Delete
    suspend fun deleteMovie(movie: Movie)

    @Transaction @Query("DELETE FROM movies")
    suspend fun deleteAllMovies()
}