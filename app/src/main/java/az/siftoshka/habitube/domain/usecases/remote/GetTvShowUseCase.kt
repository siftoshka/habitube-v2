package az.siftoshka.habitube.domain.usecases.remote

import az.siftoshka.habitube.data.remote.dto.toTvShow
import az.siftoshka.habitube.domain.model.TvShow
import az.siftoshka.habitube.domain.repository.MovieDBApiRepository
import az.siftoshka.habitube.domain.util.Resource
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

/**
 * Use-case to get tv show from repository call.
 */
class GetTvShowUseCase @Inject constructor(
    private val repository: MovieDBApiRepository
) {
    operator fun invoke(showId: Int): Flow<Resource<TvShow>> = flow {
        try {
            emit(Resource.Loading())
            val movie = repository.getTvShow(showId).toTvShow()
            delay(250L)
            emit(Resource.Success(movie))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "HTTP Error"))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection."))
        }
    }
}