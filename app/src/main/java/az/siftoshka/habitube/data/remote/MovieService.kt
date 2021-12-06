package az.siftoshka.habitube.data.remote

import az.siftoshka.habitube.data.remote.dto.MovieDto
import az.siftoshka.habitube.data.remote.dto.MovieResponseDto
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
    ): MovieResponseDto

    @GET("trending/movie/day")
    suspend fun getTrendingMovies(
        @Query("page") page: Int,
    ): MovieResponseDto

    @GET("trending/tv/day")
    suspend fun getTrendingTvShows(
        @Query("page") page: Int,
    ): MovieResponseDto

    @GET("tv/airing_today")
    suspend fun getAirTodayTvShows(
        @Query("page") page: Int,
    ): MovieResponseDto

    @GET("movie/{movie_id}")
    suspend fun getMovie(
        @Path("movie_id") movieId: Int,
    ): MovieDto
}