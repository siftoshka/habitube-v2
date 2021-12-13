package az.siftoshka.habitube.data.remote

import az.siftoshka.habitube.data.remote.dto.*
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Description of the API Queries.
 */
interface MovieService {

    /**
     * Explore ----------
     */
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

    /**
     * Movie ----------
     */
    @GET("movie/{movie_id}")
    suspend fun getMovie(
        @Path("movie_id") movieId: Int,
    ): MovieDto

    @GET("movie/{movie_id}/videos")
    suspend fun getMovieVideos(
        @Path("movie_id") movieId: Int,
    ): VideoResponseDto

    @GET("movie/{movie_id}/credits")
    suspend fun getMovieCredits(
        @Path("movie_id") movieId: Int
    ): CreditDto

    @GET("movie/{movie_id}/similar")
    suspend fun getSimilarMovies(
        @Path("movie_id") movieId: Int,
        @Query("page") page: Int,
    ): MediaResponseDto

    /**
     * TV Show ----------
     */
    @GET("tv/{tv_id}")
    suspend fun getTvShow(
        @Path("tv_id") showId: Int,
    ): TvShowDto

    @GET("tv/{tv_id}/videos")
    suspend fun getTvShowVideos(
        @Path("tv_id") showId: Int,
    ): VideoResponseDto

    @GET("tv/{tv_id}/credits")
    suspend fun getTvShowCredits(
        @Path("tv_id") showId: Int
    ): CreditDto

    @GET("tv/{tv_id}/similar")
    suspend fun getSimilarTvShows(
        @Path("tv_id") showId: Int,
        @Query("page") page: Int,
    ): MediaResponseDto

    /**
     * Movie Star ----------
     */
    @GET("person/{person_id}")
    suspend fun getPerson(
        @Path("person_id") personId: Int,
    ): PersonDto

    @GET("person/{person_id}/movie_credits")
    suspend fun getPersonMovieCredits(
        @Path("person_id") personId: Int,
    ): PersonCreditDto

    @GET("person/{person_id}/tv_credits")
    suspend fun getPersonTvShowCredits(
        @Path("person_id") personId: Int,
    ): PersonCreditDto
}