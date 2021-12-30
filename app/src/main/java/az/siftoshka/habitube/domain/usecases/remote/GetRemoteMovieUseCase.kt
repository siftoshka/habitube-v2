package az.siftoshka.habitube.domain.usecases.remote

import az.siftoshka.habitube.data.remote.dto.toMovie
import az.siftoshka.habitube.domain.model.Movie
import az.siftoshka.habitube.domain.repository.MovieDBApiRepository
import javax.inject.Inject

/**
 * Use-case to get movie from repository call.
 */
class GetRemoteMovieUseCase @Inject constructor(
    private val repository: MovieDBApiRepository
) {
    suspend operator fun invoke(movieId: Int): Movie {
        return repository.getMovie(movieId).toMovie()
    }
}