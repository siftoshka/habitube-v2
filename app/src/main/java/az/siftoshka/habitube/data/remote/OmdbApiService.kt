package az.siftoshka.habitube.data.remote

import az.siftoshka.habitube.data.remote.dto.OmdbDto
import az.siftoshka.habitube.domain.util.Constants
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Description of the API Queries from OMDb Api.
 */
interface OmdbApiService {

    @GET(Constants.OMDB_API_URL)
    suspend fun getOmdbData(
        @Query("apikey") apikey: String,
        @Query("i") imdbId: String
    ): OmdbDto
}