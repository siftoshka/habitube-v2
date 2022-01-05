package az.siftoshka.habitube.domain.repository

import az.siftoshka.habitube.data.remote.dto.OmdbDto

/**
 * Repository of OMDb API.
 */
interface OmdbApiRepository {

    suspend fun getOmdbData(imdbId: String): OmdbDto
}