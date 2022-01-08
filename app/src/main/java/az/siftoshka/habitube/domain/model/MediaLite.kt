package az.siftoshka.habitube.domain.model

data class MediaLite(
    val adult: Boolean? = false,
    val backdropPath: String? = null,
    val genreIds: List<Int>? = emptyList(),
    val id: Int? = 0,
    val originalLanguage: String? = null,
    val originalTitle: String? = null,
    val overview: String? = null,
    val popularity: Double? = 0.0,
    val posterPath: String? = null,
    val profilePath: String? = null,
    val releaseDate: String? = null,
    val title: String? = null,
    val name: String? = null,
    val video: Boolean? = false,
    val mediaType: String? = null,
    var voteAverage: Double? = 0.0,
    val voteCount: Int? = 0
)