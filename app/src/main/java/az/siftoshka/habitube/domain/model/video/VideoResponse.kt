package az.siftoshka.habitube.domain.model.video


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class VideoResponse(
    @SerialName("id")
    val id: Int,
    @SerialName("results")
    val results: List<Video>
)