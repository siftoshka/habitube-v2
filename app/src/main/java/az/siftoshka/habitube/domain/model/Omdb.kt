package az.siftoshka.habitube.domain.model

data class Omdb(
    val actors: String? = null,
    val awards: String? = null,
    val country: String? = null,
    val director: String? = null,
    val genre: String? = null,
    val imdbID: String? = null,
    val imdbRating: String? = null,
    val imdbVotes: String? = null,
    val language: String? = null,
    val metascore: String? = null,
    val plot: String? = null,
    val poster: String? = null,
    val rated: String? = null,
    val ratings: List<OmdbRating>? = emptyList(),
    val released: String? = null,
    val response: String? = null,
    val runtime: String? = null,
    val title: String? = null,
    val totalSeasons: String? = null,
    val type: String? = null,
    val writer: String? = null,
    val year: String? = null,
    val boxOffice: String? = null,
    val dVD: String? = null,
    val production: String? = null,
    val website: String? = null
)

data class OmdbRating(
    val source: String? = null,
    val value: String? = null
)