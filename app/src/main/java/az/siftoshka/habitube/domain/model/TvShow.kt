package az.siftoshka.habitube.domain.model

data class TvShow(
    val backdropPath: String? = null,
    val episodeRunTime: List<Int>? = emptyList(),
    val firstAirDate: String? = null,
    val genres: List<TvShowGenre>? = emptyList(),
    val homepage: String? = null,
    val id: Int? = 0,
    val inProduction: Boolean? = false,
    val lastAirDate: String? = null,
    val name: String? = null,
    val networks: List<Network>? = emptyList(),
    val numberOfEpisodes: Int? = 0,
    val numberOfSeasons: Int? = 0,
    val originCountry: List<String>? = emptyList(),
    val originalLanguage: String? = null,
    val originalName: String? = null,
    val overview: String? = null,
    val popularity: Double? = 0.0,
    val posterPath: String? = null,
    val seasons: List<Season>? = emptyList(),
    val status: String? = null,
    val tagline: String? = null,
    val type: String? = null,
    val voteAverage: Double? = 0.0,
    val voteCount: Int? = 0
)

data class TvShowGenre(
    val id: Int? = 0,
    val name: String? = null
)

data class Network(
    val id: Int? = 0,
    val logoPath: String? = null,
    val name: String? = null,
    val originCountry: String? = null
)

data class Season(
    val airDate: String? = null,
    val episodeCount: Int? = 0,
    val id: Int? = 0,
    val name: String? = null,
    val overview: String? = null,
    val posterPath: String? = null,
    val seasonNumber: Int? = 0
)