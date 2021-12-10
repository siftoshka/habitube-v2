package az.siftoshka.habitube.data.remote.dto

import az.siftoshka.habitube.domain.model.Video
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class VideoResponseDto(
    @SerializedName("id")
    @Expose val id: Int,
    @SerializedName("results")
    @Expose val results: List<VideoDto>
)

data class VideoDto(
    @SerializedName("id") @Expose val id: String,
    @SerializedName("iso_3166_1") @Expose val iso31661: String,
    @SerializedName("iso_639_1") @Expose val iso6391: String,
    @SerializedName("key") @Expose val key: String,
    @SerializedName("name") @Expose val name: String,
    @SerializedName("official") @Expose val official: Boolean,
    @SerializedName("published_at") @Expose val publishedAt: String,
    @SerializedName("site") @Expose val site: String,
    @SerializedName("size") @Expose val size: Int,
    @SerializedName("type") @Expose val type: String
)

fun VideoDto.toVideo() : Video {
    return this.let {
        Video(
            id = it.id,
            iso31661 = it.iso31661,
            iso6391 = it.iso6391,
            key = it.key,
            name = it.name,
            official = it.official,
            publishedAt = it.publishedAt,
            site = it.site,
            size = it.size,
            type = it.type
        )
    }
}