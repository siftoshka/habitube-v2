package az.siftoshka.habitube.domain.model

data class Video(
    val id: String? = null,
    val iso31661: String? = null,
    val iso6391: String? = null,
    val key: String? = null,
    val name: String? = null,
    val official: Boolean? = false,
    val publishedAt: String? = null,
    val site: String? = null,
    val size: Int? = 0,
    val type: String? = null
)