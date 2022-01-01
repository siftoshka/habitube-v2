package az.siftoshka.habitube.domain.usecases.remote

import az.siftoshka.habitube.data.remote.dto.toTvShow
import az.siftoshka.habitube.domain.model.TvShow
import az.siftoshka.habitube.domain.repository.MovieDBApiRepository
import javax.inject.Inject

/**
 * Use-case to get tv show from repository call.
 */
class GetRemoteShowUseCase @Inject constructor(
    private val repository: MovieDBApiRepository
) {
    suspend operator fun invoke(showId: Int): TvShow {
        return repository.getTvShow(showId).toTvShow()
    }
}