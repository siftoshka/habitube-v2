package az.siftoshka.habitube.domain.usecases.local

import az.siftoshka.habitube.domain.model.TvShow
import az.siftoshka.habitube.domain.repository.LocalRepository
import az.siftoshka.habitube.domain.repository.RealtimeRepository
import az.siftoshka.habitube.domain.repository.WatchedRepository
import az.siftoshka.habitube.presentation.screens.settings.sort.SortType
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.*
import javax.inject.Inject

/**
 * Use-case to get watched tv shows calls from repository.
 */
class WatchedTvShowUseCase @Inject constructor(
    private val repository: WatchedRepository,
    private val localRepository: LocalRepository,
    private val realtimeRepository: RealtimeRepository
) {

    suspend fun addShow(show: TvShow, rating: Float?) {
        repository.addShow(show.copy(addedDate = Date(), myRating = rating))
        val user = FirebaseAuth.getInstance().currentUser
        if (user != null) {
            realtimeRepository.addShowAsWatched(show = show.copy(myRating = rating), user = user)
        }
    }

    suspend fun deleteShow(show: TvShow) {
        repository.deleteShow(show)
        val user = FirebaseAuth.getInstance().currentUser
        if (user != null) {
            realtimeRepository.deleteShowFromWatched(show, user)
        }
    }

    suspend fun deleteAllShows() {
        repository.deleteAllShows()
        val user = FirebaseAuth.getInstance().currentUser
        if (user != null) {
            realtimeRepository.deleteAllWatchedShows(user)
        }
    }

    suspend fun getShow(showId: Int): TvShow {
        return repository.getShow(showId) ?: TvShow()
    }

    suspend fun getShowRating(showId: Int): Float {
        return repository.getShowRating(showId) ?: 0f
    }

    suspend fun isShowExist(showId: Int): Boolean {
        return repository.isShowExist(showId)
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