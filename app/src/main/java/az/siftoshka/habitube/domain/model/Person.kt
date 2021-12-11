package az.siftoshka.habitube.domain.model

data class Person(
    val adult: Boolean? = false,
    val alsoKnownAs: List<String>? = emptyList(),
    val biography: String? = null,
    val birthday: String? = null,
    val deathday: String? = null,
    val gender: Int? = 0,
    val homepage: String? = null,
    val id: Int? = 0,
    val imdbId: String? = null,
    val knownForDepartment: String? = null,
    val name: String? = null,
    val placeOfBirth: String? = null,
    val popularity: Double? = 0.0,
    val profilePath: String? = null
)