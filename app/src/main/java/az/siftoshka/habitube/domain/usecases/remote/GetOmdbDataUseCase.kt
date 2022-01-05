package az.siftoshka.habitube.domain.usecases.remote

import az.siftoshka.habitube.data.remote.dto.toData
import az.siftoshka.habitube.domain.model.Omdb
import az.siftoshka.habitube.domain.model.OmdbRating
import az.siftoshka.habitube.domain.repository.OmdbApiRepository
import az.siftoshka.habitube.domain.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

/**
 * Use-case to get ratings of the movie from OMDb Api.
 */
class GetOmdbDataUseCase @Inject constructor(private val repository: OmdbApiRepository) {
    operator fun invoke(imdbId: String): Flow<Resource<Omdb>> = flow {
        try {
            emit(Resource.Loading())
            val movie = repository.getOmdbData(imdbId).toData()
            emit(Resource.Success(movie))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "HTTP Error"))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection."))
        }
    }
}