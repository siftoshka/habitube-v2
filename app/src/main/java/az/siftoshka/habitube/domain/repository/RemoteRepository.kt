package az.siftoshka.habitube.domain.repository

import az.siftoshka.habitube.data.remote.dto.MovieDto
import az.siftoshka.habitube.data.remote.dto.MovieLiteDto

/**
 * Remote repository of application.
 */
interface RemoteRepository {

    suspend fun getUpcomingMovies(page: Int) : List<MovieLiteDto>

    suspend fun getTrendingMovies(page: Int) : List<MovieLiteDto>

    suspend fun getTrendingTvShows(page: Int) : List<MovieLiteDto>

    suspend fun getAirTodayTvShows(page: Int) : List<MovieLiteDto>

    suspend fun getMovie(movieId: Int) : MovieDto

}