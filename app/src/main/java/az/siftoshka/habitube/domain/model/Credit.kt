package az.siftoshka.habitube.domain.model

data class Credit(
    val cast: List<Cast>? = emptyList(),
    val crew: List<Crew>? = emptyList(),
    val id: Int? = 0
)

data class Cast(
    val adult: Boolean? = false,
    val castId: Int? = 0,
    val character: String? = null,
    val creditId: String? = null,
    val gender: Int? = 0,
    val id: Int? = 0,
    val knownForDepartment: String? = null,
    val name: String? = null,
    val order: Int? = 0,
    val originalName: String? = null,
    val popularity: Double? = 0.0,
    val profilePath: String? = null
)

data class Crew(
    val adult: Boolean? = false,
    val creditId: String? = null,
    val department: String? = null,
    val gender: Int? = 0,
    val id: Int? = 0,
    val job: String? = null,
    val knownForDepartment: String? = null,
    val name: String? = null,
    val originalName: String? = null,
    val popularity: Double? = 0.0,
    val profilePath: String? = null
)