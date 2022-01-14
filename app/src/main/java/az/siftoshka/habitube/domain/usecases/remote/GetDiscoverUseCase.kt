package az.siftoshka.habitube.domain.usecases.remote

import az.siftoshka.habitube.data.remote.dto.toMediaLite
import az.siftoshka.habitube.domain.model.MediaLite
import az.siftoshka.habitube.domain.repository.MovieDBApiRepository
import az.siftoshka.habitube.domain.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

/**
 * Use-case to get discover content from repository call.
 */
class GetDiscoverUseCase @Inject constructor(
    private val repository: MovieDBApiRepository
) {
    operator fun invoke(
        sort: String,
        genres: String,
        networks: String,
        yearGte: String,
        yearLte: String,
        ratingGte: String,
        ratingLte: String,
        page: Int,
        isMovieSelected: Boolean
    ): Flow<Resource<List<MediaLite>>> = flow {
        try {
            emit(Resource.Loading())
            var resources = if (isMovieSelected) {
                repository.getDiscoverMovies(sort, genres, yearGte, yearLte, ratingGte, ratingLte, page).map { it.toMediaLite() }
            } else {
                repository.getDiscoverTvShows(sort, genres, networks, yearGte, yearLte, ratingGte, ratingLte, page).map { it.toMediaLite() }
            }
            resources = resources.filterNot { it.posterPath.isNullOrBlank() }
            emit(Resource.Success(resources))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "HTTP Error"))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection."))
        }
    }
}