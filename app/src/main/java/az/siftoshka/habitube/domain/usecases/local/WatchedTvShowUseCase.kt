package az.siftoshka.habitube.domain.usecases.local

import az.siftoshka.habitube.domain.model.TvShow
import az.siftoshka.habitube.domain.repository.WatchedRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.*
import javax.inject.Inject

/**
 * Use-case tp get watched tv shows calls from repository.
 */
class WatchedTvShowUseCase @Inject constructor(
    private val repository: WatchedRepository
) {

    suspend fun addShow(show: TvShow, rating: Float?) {
        repository.addShow(show.copy(addedDate = Date(), myRating = rating))
    }

    suspend fun updateShow(show: TvShow) {
        repository.updateShow(show)
    }

    suspend fun deleteShow(show: TvShow) {
        repository.deleteShow(show)
    }

    suspend fun deleteAllShows() {
        repository.deleteAllShows()
    }

    suspend fun getShow(showId: Int): TvShow {
        return repository.getShow(showId) ?: TvShow()
    }

    fun getShows(): Flow<List<TvShow>> {
        return repository.getShows().map { shows ->
            shows.sortedBy { it.name }
        }
    }
}