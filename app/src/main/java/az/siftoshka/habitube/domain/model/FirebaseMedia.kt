package az.siftoshka.habitube.domain.model

data class FirebaseMedia(
    var id: Int? = 0,
    var rate: Float? = 0f
)

data class FirebaseShowMedia(
    var id: Int? = 0,
    var rate: Float? = 0f,
    var seasons: List<Int>? = null
)