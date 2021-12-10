package az.siftoshka.habitube.data.remote

import az.siftoshka.habitube.data.remote.dto.MediaResponseDto
import az.siftoshka.habitube.data.remote.dto.MovieDto
import az.siftoshka.habitube.data.remote.dto.TvShowDto
import az.siftoshka.habitube.data.remote.dto.VideoResponseDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Description of the API Queries.
 */
interface MovieService {

    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(
        @Query("page") page: Int,
    ): MediaResponseDto

    @GET("trending/movie/day")
    suspend fun getTrendingMovies(
        @Query("page") page: Int,
    ): MediaResponseDto

    @GET("trending/tv/day")
    suspend fun getTrendingTvShows(
        @Query("page") page: Int,
    ): MediaResponseDto

    @GET("tv/airing_today")
    suspend fun getAirTodayTvShows(
        @Query("page") page: Int,
    ): MediaResponseDto

    @GET("movie/{movie_id}")
    suspend fun getMovie(
        @Path("movie_id") movieId: Int,
    ): MovieDto

    @GET("movie/{movie_id}/videos")
    suspend fun getMovieVideos(
        @Path("movie_id") movieId: Int,
    ): VideoResponseDto

    @GET("tv/{tv_id}")
    suspend fun getTvShow(
        @Path("tv_id") showId: Int,
    ): TvShowDto

    @GET("tv/{tv_id}/videos")
    suspend fun getTvShowVideos(
        @Path("tv_id") showId: Int,
    ): VideoResponseDto
}