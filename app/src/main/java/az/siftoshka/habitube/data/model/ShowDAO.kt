package az.siftoshka.habitube.data.model

import androidx.room.*
import az.siftoshka.habitube.domain.model.Movie
import az.siftoshka.habitube.domain.model.TvShow
import kotlinx.coroutines.flow.Flow

/**
 * The DAO interface of the [ShowDatabase].
 */
@Dao
interface ShowDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addShow(show: TvShow)

    @Update
    suspend fun updateShow(show: TvShow)

    @Transaction @Query("SELECT * FROM shows")
    fun getShows() : Flow<List<TvShow>>

    @Transaction @Query("SELECT * FROM shows WHERE id = :showId")
    suspend fun getShow(showId: Int) : TvShow?

    @Delete
    suspend fun deleteShow(show: TvShow)

    @Transaction @Query("DELETE FROM shows")
    suspend fun deleteAllShows()
}