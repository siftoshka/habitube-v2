package az.siftoshka.habitube.domain.model

data class Movie(
    val adult: Boolean? = false,
    val backdropPath: String? = null,
    val budget: Int? = 0,
    val genres: List<MovieGenre>? = emptyList(),
    val homepage: String? = null,
    val id: Int? = 0,
    val imdbId: String? = null,
    val originalLanguage: String? = null,
    val originalTitle: String? = null,
    val overview: String? = null,
    val popularity: Double? = 0.0,
    val posterPath: String? = null,
    val productionCompanies: List<ProductionCompany>? = emptyList(),
    val productionCountries: List<ProductionCountry>? = emptyList(),
    val releaseDate: String? = null,
    val revenue: Int? = 0,
    val runtime: Int? = 0,
    val spokenLanguages: List<SpokenLanguage>? = emptyList(),
    val status: String? = null,
    val tagline: String? = null,
    val title: String? = null,
    val video: Boolean? = false,
    val voteAverage: Double? = 0.0,
    val voteCount: Int? = 0
)

data class MovieGenre(
    val id: Int? = 0,
    val name: String? = null
)

data class ProductionCompany(
    val id: Int? = 0,
    val logoPath: String? = null,
    val name: String? = null,
    val originCountry: String? = null
)

data class ProductionCountry(
    val iso31661: String? = null,
    val name: String? = null
)

data class SpokenLanguage(
    val englishName: String? = null,
    val iso6391: String? = null,
    val name: String? = null
)