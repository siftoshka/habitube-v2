package az.siftoshka.habitube.data.remote.dto


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class TvShowDto(
    @SerializedName("backdrop_path") @Expose val backdropPath: String,
    @SerializedName("episode_run_time") @Expose val episodeRunTime: List<Int>,
    @SerializedName("first_air_date") @Expose val firstAirDate: String,
    @SerializedName("genres") @Expose val genres: List<TvShowGenre>,
    @SerializedName("homepage") @Expose val homepage: String,
    @SerializedName("id") @Expose val id: Int,
    @SerializedName("in_production") @Expose val inProduction: Boolean,
    @SerializedName("last_air_date") @Expose val lastAirDate: String,
    @SerializedName("name") @Expose val name: String,
    @SerializedName("networks") @Expose val networks: List<Network>,
    @SerializedName("number_of_episodes") @Expose val numberOfEpisodes: Int,
    @SerializedName("number_of_seasons") @Expose val numberOfSeasons: Int,
    @SerializedName("origin_country") @Expose val originCountry: List<String>,
    @SerializedName("original_language") @Expose val originalLanguage: String,
    @SerializedName("original_name") @Expose val originalName: String,
    @SerializedName("overview") @Expose val overview: String,
    @SerializedName("popularity") @Expose val popularity: Double,
    @SerializedName("poster_path") @Expose val posterPath: String,
    @SerializedName("seasons") @Expose val seasons: List<Season>,
    @SerializedName("status") @Expose val status: String,
    @SerializedName("tagline") @Expose val tagline: String,
    @SerializedName("type") @Expose val type: String,
    @SerializedName("vote_average") @Expose val voteAverage: Double,
    @SerializedName("vote_count") @Expose val voteCount: Int
)

data class TvShowGenre(
    @SerializedName("id") @Expose val id: Int,
    @SerializedName("name") @Expose val name: String
)

data class Network(
    @SerializedName("id") @Expose val id: Int,
    @SerializedName("logo_path") @Expose val logoPath: String,
    @SerializedName("name") @Expose val name: String,
    @SerializedName("origin_country") @Expose val originCountry: String
)

data class Season(
    @SerializedName("air_date") @Expose val airDate: String,
    @SerializedName("episode_count") @Expose val episodeCount: Int,
    @SerializedName("id") @Expose val id: Int,
    @SerializedName("name") @Expose val name: String,
    @SerializedName("overview") @Expose val overview: String,
    @SerializedName("poster_path") @Expose val posterPath: String,
    @SerializedName("season_number") @Expose val seasonNumber: Int
)