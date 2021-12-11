package az.siftoshka.habitube.domain.repository

import az.siftoshka.habitube.data.remote.dto.CreditDto
import az.siftoshka.habitube.data.remote.dto.MovieDto
import az.siftoshka.habitube.data.remote.dto.MediaLiteDto
import az.siftoshka.habitube.data.remote.dto.VideoDto

/**
 * Remote repository of application.
 */
interface RemoteRepository {

    suspend fun getUpcomingMovies(page: Int) : List<MediaLiteDto>

    suspend fun getTrendingMovies(page: Int) : List<MediaLiteDto>

    suspend fun getTrendingTvShows(page: Int) : List<MediaLiteDto>

    suspend fun getAirTodayTvShows(page: Int) : List<MediaLiteDto>

    suspend fun getMovie(movieId: Int) : MovieDto

    suspend fun getMovieVideos(movieId: Int) : List<VideoDto>

    suspend fun getMovieCredits(movieId: Int) : CreditDto

    suspend fun getSimilarMovies(movieId: Int, page: Int) : List<MediaLiteDto>

}