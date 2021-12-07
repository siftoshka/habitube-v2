package az.siftoshka.habitube.domain.repository

import az.siftoshka.habitube.data.remote.dto.MovieDto
import az.siftoshka.habitube.data.remote.dto.MediaLiteDto

/**
 * Remote repository of application.
 */
interface RemoteRepository {

    suspend fun getUpcomingMovies(page: Int) : List<MediaLiteDto>

    suspend fun getTrendingMovies(page: Int) : List<MediaLiteDto>

    suspend fun getTrendingTvShows(page: Int) : List<MediaLiteDto>

    suspend fun getAirTodayTvShows(page: Int) : List<MediaLiteDto>

    suspend fun getMovie(movieId: Int) : MovieDto

}