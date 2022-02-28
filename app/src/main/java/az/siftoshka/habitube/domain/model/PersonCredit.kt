package az.siftoshka.habitube.domain.model

data class PersonCredit(
    val cast: List<PersonCast>? = emptyList(),
    val crew: List<PersonCrew>? = emptyList(),
    val id: Int? = 0
)

data class PersonCast(
    val backdropPath: String? = null,
    val character: String? = null,
    val creditId: String? = null,
    val episodeCount: Int? = 0,
    val firstAirDate: String? = null,
    val genreIds: List<Int>? = emptyList(),
    val id: Int? = 0,
    val name: String? = null,
    val originCountry: List<String>? = emptyList(),
    val originalLanguage: String? = null,
    val originalName: String? = null,
    val overview: String? = null,
    val popularity: Double? = 0.0,
    val posterPath: String? = null,
    var voteAverage: Double? = 0.0,
    val voteCount: Int? = 0
)

data class PersonCrew(
    val backdropPath: String? = null,
    val creditId: String? = null,
    val department: String? = null,
    val episodeCount: Int? = 0,
    val firstAirDate: String? = null,
    val genreIds: List<Int>? = emptyList(),
    val id: Int? = 0,
    val job: String? = null,
    val name: String? = null,
    val originCountry: List<String>? = emptyList(),
    val originalLanguage: String? = null,
    val originalName: String? = null,
    val overview: String? = null,
    val popularity: Double? = 0.0,
    val posterPath: String? = null,
    var voteAverage: Double? = 0.0,
    val voteCount: Int? = 0
)