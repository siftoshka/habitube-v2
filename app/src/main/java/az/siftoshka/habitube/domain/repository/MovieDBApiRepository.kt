package az.siftoshka.habitube.domain.repository

import az.siftoshka.habitube.data.remote.dto.*

/**
 * Repository of TheMovieDB API.
 */
interface MovieDBApiRepository {

    suspend fun getUpcomingMovies(page: Int): List<MediaLiteDto>

    suspend fun getTrendingMovies(page: Int): List<MediaLiteDto>

    suspend fun getTrendingTvShows(page: Int): List<MediaLiteDto>

    suspend fun getAirTodayTvShows(page: Int): List<MediaLiteDto>

    suspend fun getMovie(movieId: Int): MovieDto

    suspend fun getMovieVideos(movieId: Int): List<VideoDto>

    suspend fun getMovieCredits(movieId: Int): CreditDto

    suspend fun getSimilarMovies(movieId: Int, page: Int): List<MediaLiteDto>

    suspend fun getTvShow(showId: Int): TvShowDto

    suspend fun getTvShowVideos(showId: Int): List<VideoDto>

    suspend fun getTvShowCredits(showId: Int): CreditDto

    suspend fun getSimilarTvShows(showId: Int, page: Int): List<MediaLiteDto>

    suspend fun getPerson(personId: Int): PersonDto

    suspend fun getPersonMovieCredits(personId: Int): PersonCreditDto

    suspend fun getPersonTvShowCredits(personId: Int): PersonCreditDto

    suspend fun getSearchResults(searchQuery: String, page: Int): List<MediaLiteDto>

    suspend fun getMovieSearchResults(searchQuery: String, page: Int): List<MediaLiteDto>

    suspend fun getTvShowSearchResults(searchQuery: String, page: Int): List<MediaLiteDto>

    suspend fun getPersonSearchResults(searchQuery: String, page: Int): List<MediaLiteDto>

    suspend fun getDiscoverMovies(
        sort: String,
        genres: String,
        yearGte: String,
        yearLte: String,
        ratingGte: String,
        ratingLte: String,
        page: Int
    ): List<MediaLiteDto>

    suspend fun getDiscoverTvShows(
        sort: String,
        genres: String,
        networks: String,
        yearGte: String,
        yearLte: String,
        ratingGte: String,
        ratingLte: String,
        page: Int
    ): List<MediaLiteDto>
}