package az.siftoshka.habitube.domain.usecases.remote

import az.siftoshka.habitube.data.remote.dto.toCredits
import az.siftoshka.habitube.domain.model.Credit
import az.siftoshka.habitube.domain.repository.MovieDBApiRepository
import az.siftoshka.habitube.domain.util.MediaType
import az.siftoshka.habitube.domain.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

/**
 * Use-case to get credits from repository call.
 */
class GetCreditsUseCase @Inject constructor(
    private val repository: MovieDBApiRepository
) {
    operator fun invoke(mediaId: Int, mediaType: MediaType): Flow<Resource<Credit>> = flow {
        try {
            emit(Resource.Loading())
            var credits = when (mediaType) {
                MediaType.Movie -> repository.getMovieCredits(mediaId).toCredits()
                MediaType.TvShow -> repository.getTvShowCredits(mediaId).toCredits()
            }
            val cast = credits.cast
                ?.distinctBy { it.name }
                ?.sortedByDescending { it.popularity }
            val crew = credits.crew
                ?.distinctBy { it.name }
                ?.sortedByDescending { it.popularity }

            credits = credits.copy(cast = cast, crew = crew)
            emit(Resource.Success(credits))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "HTTP Error"))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection."))
        }
    }
}