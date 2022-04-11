package az.siftoshka.habitube.domain.usecases.remote

import az.siftoshka.habitube.data.remote.dto.toMediaLite
import az.siftoshka.habitube.domain.model.MediaLite
import az.siftoshka.habitube.domain.repository.MovieDBApiRepository
import az.siftoshka.habitube.domain.util.Resource
import az.siftoshka.habitube.domain.util.SearchType
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

/**
 * Use-case to get search content from repository call.
 */
class GetSearchUseCase @Inject constructor(
    private val repository: MovieDBApiRepository
) {
    operator fun invoke(searchQuery: String, page: Int, type: SearchType): Flow<Resource<List<MediaLite>>> = flow {
        try {
            emit(Resource.Loading())
            delay(500L)
            val resources = when (type) {
                SearchType.Multi -> repository.getSearchResults(searchQuery, page).map { it.toMediaLite() }
                SearchType.Movie -> repository.getMovieSearchResults(searchQuery, page).map { it.toMediaLite() }
                SearchType.TvShow -> repository.getTvShowSearchResults(searchQuery, page).map { it.toMediaLite() }
                SearchType.Person -> repository.getPersonSearchResults(searchQuery, page).map { it.toMediaLite() }
            }
            emit(Resource.Success(resources.sortedByDescending { it.popularity }))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "HTTP Error"))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection."))
        }
    }
}