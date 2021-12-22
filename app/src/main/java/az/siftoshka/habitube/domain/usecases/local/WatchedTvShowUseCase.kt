package az.siftoshka.habitube.domain.usecases.local

import az.siftoshka.habitube.domain.model.TvShow
import az.siftoshka.habitube.domain.repository.LocalRepository
import az.siftoshka.habitube.domain.repository.WatchedRepository
import az.siftoshka.habitube.presentation.screens.settings.sort.SortType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.*
import javax.inject.Inject

/**
 * Use-case tp get watched tv shows calls from repository.
 */
class WatchedTvShowUseCase @Inject constructor(
    private val repository: WatchedRepository,
    private val localRepository: LocalRepository
) {

    suspend fun addShow(show: TvShow, rating: Float?) {
        repository.addShow(show.copy(addedDate = Date(), myRating = rating))
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
            when (localRepository.getSortType()) {
                SortType.RECENTLY -> shows.sortedByDescending { it.addedDate }
                SortType.FIRST -> shows.sortedBy { it.addedDate }
                SortType.TITLE -> shows.sortedBy { it.name }
                SortType.RATING -> shows.sortedByDescending { it.myRating }
                SortType.RELEASE_DESC -> shows.sortedByDescending { it.firstAirDate }
                SortType.RELEASE_ASC -> shows.sortedBy { it.firstAirDate }
            }
        }
    }
}