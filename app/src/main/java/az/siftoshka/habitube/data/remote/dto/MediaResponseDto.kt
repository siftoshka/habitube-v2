package az.siftoshka.habitube.data.remote.dto

import az.siftoshka.habitube.domain.model.MediaLite
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class MediaResponseDto(
    @SerializedName("page") @Expose val page: Int,
    @SerializedName("results") @Expose val results: List<MediaLiteDto>,
    @SerializedName("total_pages") @Expose val totalPages: Int,
    @SerializedName("total_results") @Expose val totalResults: Int
)

data class MediaLiteDto(
    @SerializedName("adult") @Expose val adult: Boolean,
    @SerializedName("backdrop_path") @Expose val backdropPath: String,
    @SerializedName("genre_ids") @Expose val genreIds: List<Int>,
    @SerializedName("id") @Expose val id: Int,
    @SerializedName("original_language") @Expose val originalLanguage: String,
    @SerializedName("original_title") @Expose val originalTitle: String,
    @SerializedName("overview") @Expose val overview: String,
    @SerializedName("popularity") @Expose val popularity: Double,
    @SerializedName("poster_path") @Expose val posterPath: String,
    @SerializedName("release_date") @Expose val releaseDate: String,
    @SerializedName("title") @Expose val title: String,
    @SerializedName("video") @Expose val video: Boolean,
    @SerializedName("vote_average") @Expose val voteAverage: Double,
    @SerializedName("vote_count") @Expose val voteCount: Int
)

fun MediaLiteDto.toMediaLite(): MediaLite {
    return this.let {
        MediaLite(
            adult = it.adult,
            backdropPath = it.backdropPath,
            genreIds = it.genreIds,
            id = it.id,
            originalLanguage = it.originalLanguage,
            originalTitle = it.originalTitle,
            overview = it.overview,
            popularity = it.popularity,
            posterPath = it.posterPath,
            releaseDate = it.releaseDate,
            title = it.title,
            video = it.video,
            voteAverage = it.voteAverage,
            voteCount = it.voteCount
        )
    }
}