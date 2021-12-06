package az.siftoshka.habitube.domain.usecases

import az.siftoshka.habitube.data.remote.dto.toMovieLite
import az.siftoshka.habitube.domain.model.MovieLite
import az.siftoshka.habitube.domain.repository.RemoteRepository
import az.siftoshka.habitube.domain.util.ExploreType
import az.siftoshka.habitube.domain.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

/**
 * Use-case to get trending movies from repository call.
 */
class GetExploreUseCase @Inject constructor(
    private val repository: RemoteRepository
) {
    operator fun invoke(page: Int, type: ExploreType) : Flow<Resource<List<MovieLite>>> = flow {
        try {
            emit(Resource.Loading())
            val movies = when (type) {
                ExploreType.Upcoming -> repository.getUpcomingMovies(page).map { toMovieLite(it) }
                ExploreType.TrendingMovies -> repository.getTrendingMovies(page).map { toMovieLite(it) }
                ExploreType.TrendingTvShows -> repository.getTrendingTvShows(page).map { toMovieLite(it) }
                ExploreType.AirToday -> repository.getAirTodayTvShows(page).map { toMovieLite(it) }
            }
            emit(Resource.Success(movies))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "HTTP Error"))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection."))
        }
    }
}