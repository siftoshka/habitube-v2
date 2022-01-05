package az.siftoshka.habitube.data.repository

import az.siftoshka.habitube.BuildConfig
import az.siftoshka.habitube.data.remote.OmdbApiService
import az.siftoshka.habitube.data.remote.dto.OmdbDto
import az.siftoshka.habitube.domain.repository.OmdbApiRepository
import javax.inject.Inject

/**
 * Implementation of the [OmdbApiRepository].
 */
class OmdbApiRepositoryImpl @Inject constructor(
    private val service: OmdbApiService
) : OmdbApiRepository {

    override suspend fun getOmdbData(imdbId: String): OmdbDto {
        return service.getOmdbData(BuildConfig.OMDB_API_KEY, imdbId)
    }
}