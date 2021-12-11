package az.siftoshka.habitube.data.remote.dto

import az.siftoshka.habitube.domain.model.Network
import az.siftoshka.habitube.domain.model.Season
import az.siftoshka.habitube.domain.model.TvShow
import az.siftoshka.habitube.domain.model.TvShowGenre
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class TvShowDto(
    @SerializedName("backdrop_path") @Expose val backdropPath: String,
    @SerializedName("episode_run_time") @Expose val episodeRunTime: List<Int>,
    @SerializedName("first_air_date") @Expose val firstAirDate: String,
    @SerializedName("genres") @Expose val genres: List<TvShowGenreDto>,
    @SerializedName("homepage") @Expose val homepage: String,
    @SerializedName("id") @Expose val id: Int,
    @SerializedName("in_production") @Expose val inProduction: Boolean,
    @SerializedName("last_air_date") @Expose val lastAirDate: String,
    @SerializedName("name") @Expose val name: String,
    @SerializedName("networks") @Expose val networks: List<NetworkDto>,
    @SerializedName("number_of_episodes") @Expose val numberOfEpisodes: Int,
    @SerializedName("number_of_seasons") @Expose val numberOfSeasons: Int,
    @SerializedName("origin_country") @Expose val originCountry: List<String>,
    @SerializedName("original_language") @Expose val originalLanguage: String,
    @SerializedName("original_name") @Expose val originalName: String,
    @SerializedName("overview") @Expose val overview: String,
    @SerializedName("popularity") @Expose val popularity: Double,
    @SerializedName("poster_path") @Expose val posterPath: String,
    @SerializedName("seasons") @Expose val seasons: List<SeasonDto>,
    @SerializedName("status") @Expose val status: String,
    @SerializedName("tagline") @Expose val tagline: String,
    @SerializedName("type") @Expose val type: String,
    @SerializedName("vote_average") @Expose val voteAverage: Double,
    @SerializedName("vote_count") @Expose val voteCount: Int
)

data class TvShowGenreDto(
    @SerializedName("id") @Expose val id: Int,
    @SerializedName("name") @Expose val name: String
)

data class NetworkDto(
    @SerializedName("id") @Expose val id: Int,
    @SerializedName("logo_path") @Expose val logoPath: String,
    @SerializedName("name") @Expose val name: String,
    @SerializedName("origin_country") @Expose val originCountry: String
)

data class SeasonDto(
    @SerializedName("air_date") @Expose val airDate: String,
    @SerializedName("episode_count") @Expose val episodeCount: Int,
    @SerializedName("id") @Expose val id: Int,
    @SerializedName("name") @Expose val name: String,
    @SerializedName("overview") @Expose val overview: String,
    @SerializedName("poster_path") @Expose val posterPath: String,
    @SerializedName("season_number") @Expose val seasonNumber: Int
)

fun TvShowDto.toTvShow() : TvShow {
    return this.let {
        TvShow(
            backdropPath = it.backdropPath,
            episodeRunTime = it.episodeRunTime,
            firstAirDate = it.firstAirDate,
            genres = map(it.genres),
            homepage = it.homepage,
            id = it.id,
            inProduction = it.inProduction,
            lastAirDate = it.lastAirDate,
            name = it.name,
            networks = map(it.networks),
            numberOfEpisodes = it.numberOfEpisodes,
            numberOfSeasons = it.numberOfSeasons,
            originCountry = it.originCountry,
            originalLanguage = it.originalLanguage,
            originalName = it.originalName,
            overview = it.overview,
            popularity = it.popularity,
            posterPath = it.posterPath,
            seasons = map(it.seasons),
            status = it.status,
            tagline = it.tagline,
            type = it.type,
            voteAverage = it.voteAverage,
            voteCount = it.voteCount
        )
    }
}

@JvmName("mapGenres")
fun map(genres: List<TvShowGenreDto>) : List<TvShowGenre> {
    return genres.map {
        TvShowGenre(
            id = it.id,
            name = it.name
        )
    }
}

@JvmName("mapNetworks")
fun map(networks: List<NetworkDto>) : List<Network> {
    return networks.map {
        Network(
            id = it.id,
            logoPath = it.logoPath,
            name = it.name,
            originCountry = it.originCountry
        )
    }
}

@JvmName("mapSeasons")
fun map(seasons: List<SeasonDto>) : List<Season> {
    return seasons.map {
        Season(
            airDate = it.airDate,
            episodeCount = it.episodeCount,
            id = it.id,
            name = it.name,
            overview = it.overview,
            posterPath = it.posterPath,
            seasonNumber = it.seasonNumber
        )
    }
}