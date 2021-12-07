package az.siftoshka.habitube.data.repository

import az.siftoshka.habitube.data.remote.MovieService
import az.siftoshka.habitube.data.remote.dto.MovieDto
import az.siftoshka.habitube.data.remote.dto.MediaLiteDto
import az.siftoshka.habitube.domain.repository.RemoteRepository
import javax.inject.Inject

/**
 * Implementation of the [RemoteRepository] of application.
 */
class RemoteRepositoryImpl @Inject constructor(
    private val service: MovieService
) : RemoteRepository {

    override suspend fun getUpcomingMovies(page: Int): List<MediaLiteDto> {
        return service.getUpcomingMovies(page).results
    }

    override suspend fun getTrendingMovies(page: Int): List<MediaLiteDto> {
        return service.getTrendingMovies(page).results
    }

    override suspend fun getTrendingTvShows(page: Int): List<MediaLiteDto> {
        return service.getTrendingTvShows(page).results
    }

    override suspend fun getAirTodayTvShows(page: Int): List<MediaLiteDto> {
        return service.getAirTodayTvShows(page).results
    }

    override suspend fun getMovie(movieId: Int): MovieDto {
        return service.getMovie(movieId)
    }
}