package az.siftoshka.habitube.domain.usecases.local

import az.siftoshka.habitube.domain.model.TvShow
import az.siftoshka.habitube.domain.repository.PlannedRepository
import az.siftoshka.habitube.domain.repository.WatchedRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * Use-case tp get planned tv shows calls from repository.
 */
class PlannedTvShowUseCase @Inject constructor(
    private val repository: PlannedRepository
) {

    suspend fun addShow(show: TvShow) {
        repository.addShow(show)
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