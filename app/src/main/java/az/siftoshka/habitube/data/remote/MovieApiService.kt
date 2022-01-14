package az.siftoshka.habitube.data.remote

import az.siftoshka.habitube.data.remote.dto.*
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Description of the API Queries.
 */
interface MovieApiService {

    /**
     * Explore ----------
     */
    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(
        @Query("page") page: Int,
        @Query("language") language: String
    ): MediaResponseDto

    @GET("trending/movie/day")
    suspend fun getTrendingMovies(
        @Query("page") page: Int,
        @Query("language") language: String
    ): MediaResponseDto

    @GET("trending/tv/day")
    suspend fun getTrendingTvShows(
        @Query("page") page: Int,
        @Query("language") language: String
    ): MediaResponseDto

    @GET("tv/airing_today")
    suspend fun getAirTodayTvShows(
        @Query("page") page: Int,
        @Query("language") language: String
    ): MediaResponseDto

    /**
     * Movie ----------
     */
    @GET("movie/{movie_id}")
    suspend fun getMovie(
        @Path("movie_id") movieId: Int,
        @Query("language") language: String
    ): MovieDto

    @GET("movie/{movie_id}/videos")
    suspend fun getMovieVideos(
        @Path("movie_id") movieId: Int,
        @Query("language") language: String
    ): VideoResponseDto

    @GET("movie/{movie_id}/credits")
    suspend fun getMovieCredits(
        @Path("movie_id") movieId: Int
    ): CreditDto

    @GET("movie/{movie_id}/similar")
    suspend fun getSimilarMovies(
        @Path("movie_id") movieId: Int,
        @Query("page") page: Int,
        @Query("language") language: String
    ): MediaResponseDto

    /**
     * TV Show ----------
     */
    @GET("tv/{tv_id}")
    suspend fun getTvShow(
        @Path("tv_id") showId: Int,
        @Query("language") language: String
    ): TvShowDto

    @GET("tv/{tv_id}/videos")
    suspend fun getTvShowVideos(
        @Path("tv_id") showId: Int,
        @Query("language") language: String
    ): VideoResponseDto

    @GET("tv/{tv_id}/credits")
    suspend fun getTvShowCredits(
        @Path("tv_id") showId: Int
    ): CreditDto

    @GET("tv/{tv_id}/similar")
    suspend fun getSimilarTvShows(
        @Path("tv_id") showId: Int,
        @Query("page") page: Int,
        @Query("language") language: String
    ): MediaResponseDto

    /**
     * Movie Star ----------
     */
    @GET("person/{person_id}")
    suspend fun getPerson(
        @Path("person_id") personId: Int,
        @Query("language") language: String
    ): PersonDto

    @GET("person/{person_id}/movie_credits")
    suspend fun getPersonMovieCredits(
        @Path("person_id") personId: Int,
        @Query("language") language: String
    ): PersonCreditDto

    @GET("person/{person_id}/tv_credits")
    suspend fun getPersonTvShowCredits(
        @Path("person_id") personId: Int,
        @Query("language") language: String
    ): PersonCreditDto

    /**
     * Search ----------
     */
    @GET("search/multi")
    suspend fun getSearchResults(
        @Query("query") searchQuery: String,
        @Query("page") page: Int,
        @Query("include_adult") isAdult: Boolean,
        @Query("language") language: String
    ): MediaResponseDto

    @GET("search/movie")
    suspend fun getMovieSearchResults(
        @Query("query") searchQuery: String,
        @Query("page") page: Int,
        @Query("include_adult") isAdult: Boolean,
        @Query("language") language: String
    ): MediaResponseDto

    @GET("search/tv")
    suspend fun getTvShowSearchResults(
        @Query("query") searchQuery: String,
        @Query("page") page: Int,
        @Query("include_adult") isAdult: Boolean,
        @Query("language") language: String
    ): MediaResponseDto

    @GET("search/person")
    suspend fun getPersonSearchResults(
        @Query("query") searchQuery: String,
        @Query("page") page: Int,
        @Query("include_adult") isAdult: Boolean,
        @Query("language") language: String
    ): MediaResponseDto

    /**
     * Discover ----------
     */
    @GET("discover/movie")
    suspend fun getDiscoverMovies(
        @Query("sort_by") sort: String,
        @Query("with_genres") genres: String,
        @Query("release_date.gte") yearGte: String,
        @Query("release_date.lte") yearLte: String,
        @Query("vote_average.gte") ratingGte: String,
        @Query("vote_average.lte") ratingLte: String,
        @Query("page") page: Int,
        @Query("include_adult") isAdult: Boolean,
        @Query("language") language: String
    ): MediaResponseDto

    @GET("discover/tv")
    suspend fun getDiscoverTvShows(
        @Query("sort_by") sort: String,
        @Query("with_genres") genres: String,
        @Query("with_networks") networks: String,
        @Query("first_air_date.gte") yearGte: String,
        @Query("first_air_date.lte") yearLte: String,
        @Query("vote_average.gte") ratingGte: String,
        @Query("vote_average.lte") ratingLte: String,
        @Query("page") page: Int,
        @Query("include_adult") isAdult: Boolean,
        @Query("language") language: String
    ): MediaResponseDto
}