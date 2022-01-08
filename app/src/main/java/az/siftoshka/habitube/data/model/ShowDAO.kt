package az.siftoshka.habitube.data.model

import androidx.room.*
import az.siftoshka.habitube.domain.model.TvShow
import kotlinx.coroutines.flow.Flow

/**
 * The DAO interface of the [ShowDatabase].
 */
@Dao
interface ShowDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addShow(show: TvShow)

    @Transaction @Query("SELECT * FROM shows")
    fun getShows() : Flow<List<TvShow>>

    @Transaction @Query("SELECT * FROM shows WHERE id = :showId")
    suspend fun getShow(showId: Int) : TvShow?

    @Transaction @Query("SELECT my_rating FROM shows WHERE id = :showId")
    suspend fun getShowRating(showId: Int): Float?

    @Transaction @Query("SELECT count(*) FROM shows WHERE id = :showId")
    suspend fun getShowCount(showId: Int): Int

    @Delete
    suspend fun deleteShow(show: TvShow)

    @Transaction @Query("DELETE FROM shows")
    suspend fun deleteAllShows()
}