package az.siftoshka.habitube.data.repository

import az.siftoshka.habitube.data.remote.MovieService
import az.siftoshka.habitube.data.remote.dto.*
import az.siftoshka.habitube.domain.repository.LocalRepository
import az.siftoshka.habitube.domain.repository.RemoteRepository
import javax.inject.Inject

/**
 * Implementation of the [RemoteRepository] of application.
 */
class RemoteRepositoryImpl @Inject constructor(
    private val service: MovieService,
    private val localRepository: LocalRepository
) : RemoteRepository {

    override suspend fun getUpcomingMovies(page: Int): List<MediaLiteDto> {
        return service.getUpcomingMovies(page, localRepository.getContentLanguage()).results
    }

    override suspend fun getTrendingMovies(page: Int): List<MediaLiteDto> {
        return service.getTrendingMovies(page, localRepository.getContentLanguage()).results
    }

    override suspend fun getTrendingTvShows(page: Int): List<MediaLiteDto> {
        return service.getTrendingTvShows(page, localRepository.getContentLanguage()).results
    }

    override suspend fun getAirTodayTvShows(page: Int): List<MediaLiteDto> {
        return service.getAirTodayTvShows(page, localRepository.getContentLanguage()).results
    }

    override suspend fun getMovie(movieId: Int): MovieDto {
        return service.getMovie(movieId, localRepository.getContentLanguage())
    }

    override suspend fun getMovieVideos(movieId: Int): List<VideoDto> {
        return service.getMovieVideos(movieId, localRepository.getContentLanguage()).results
    }

    override suspend fun getMovieCredits(movieId: Int): CreditDto {
        return service.getMovieCredits(movieId)
    }

    override suspend fun getSimilarMovies(movieId: Int, page: Int): List<MediaLiteDto> {
        return service.getSimilarMovies(movieId, page, localRepository.getContentLanguage()).results
    }

    override suspend fun getTvShow(showId: Int): TvShowDto {
        return service.getTvShow(showId, localRepository.getContentLanguage())
    }

    override suspend fun getTvShowVideos(showId: Int): List<VideoDto> {
        return service.getTvShowVideos(showId, localRepository.getContentLanguage()).results
    }

    override suspend fun getTvShowCredits(showId: Int): CreditDto {
        return service.getTvShowCredits(showId)
    }

    override suspend fun getSimilarTvShows(showId: Int, page: Int): List<MediaLiteDto> {
        return service.getSimilarTvShows(showId, page, localRepository.getContentLanguage()).results
    }

    override suspend fun getPerson(personId: Int): PersonDto {
        return service.getPerson(personId, localRepository.getContentLanguage())
    }

    override suspend fun getPersonMovieCredits(personId: Int): PersonCreditDto {
        return service.getPersonMovieCredits(personId, localRepository.getContentLanguage())
    }

    override suspend fun getPersonTvShowCredits(personId: Int): PersonCreditDto {
        return service.getPersonTvShowCredits(personId, localRepository.getContentLanguage())
    }

    override suspend fun getSearchResults(searchQuery: String, page: Int): List<MediaLiteDto> {
        return service.getSearchResults(searchQuery, page, localRepository.isAdultVisible(), localRepository.getContentLanguage()).results
    }

    override suspend fun getMovieSearchResults(searchQuery: String, page: Int): List<MediaLiteDto> {
        return service.getMovieSearchResults(searchQuery, page, localRepository.isAdultVisible(), localRepository.getContentLanguage()).results
    }

    override suspend fun getTvShowSearchResults(searchQuery: String, page: Int): List<MediaLiteDto> {
        return service.getTvShowSearchResults(searchQuery, page, localRepository.isAdultVisible(), localRepository.getContentLanguage()).results
    }

    override suspend fun getPersonSearchResults(searchQuery: String, page: Int): List<MediaLiteDto> {
        return service.getPersonSearchResults(searchQuery, page, localRepository.isAdultVisible(), localRepository.getContentLanguage()).results
    }
}