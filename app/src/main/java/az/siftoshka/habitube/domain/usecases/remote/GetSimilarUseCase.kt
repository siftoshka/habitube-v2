package az.siftoshka.habitube.domain.usecases.remote

import az.siftoshka.habitube.data.remote.dto.toMediaLite
import az.siftoshka.habitube.domain.model.MediaLite
import az.siftoshka.habitube.domain.repository.MovieDBApiRepository
import az.siftoshka.habitube.domain.util.MediaType
import az.siftoshka.habitube.domain.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

/**
 * Use-case to get similar movies/shows from repository call.
 */
class GetSimilarUseCase @Inject constructor(
    private val repository: MovieDBApiRepository
) {
    operator fun invoke(mediaId: Int, page: Int, mediaType: MediaType) : Flow<Resource<List<MediaLite>>> = flow {
        try {
            emit(Resource.Loading())
            val resources = when (mediaType) {
                MediaType.Movie -> repository.getSimilarMovies(mediaId, page).map { it.toMediaLite() }
                MediaType.TvShow -> repository.getSimilarTvShows(mediaId, page).map { it.toMediaLite() }
            }
            emit(Resource.Success(resources))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "HTTP Error"))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection."))
        }
    }
}