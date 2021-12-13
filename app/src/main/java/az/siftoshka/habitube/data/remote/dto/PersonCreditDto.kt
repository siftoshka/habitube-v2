package az.siftoshka.habitube.data.remote.dto

import az.siftoshka.habitube.domain.model.PersonCast
import az.siftoshka.habitube.domain.model.PersonCredit
import az.siftoshka.habitube.domain.model.PersonCrew
import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose

data class PersonCreditDto(
    @SerializedName("cast") @Expose val cast: List<PersonCastDto>,
    @SerializedName("crew") @Expose val crew: List<PersonCrewDto>,
    @SerializedName("id") @Expose val id: Int
)

data class PersonCastDto(
    @SerializedName("backdrop_path") @Expose val backdropPath: String,
    @SerializedName("character") @Expose val character: String,
    @SerializedName("credit_id") @Expose val creditId: String,
    @SerializedName("episode_count") @Expose val episodeCount: Int,
    @SerializedName("first_air_date") @Expose val firstAirDate: String,
    @SerializedName("genre_ids") @Expose val genreIds: List<Int>,
    @SerializedName("id") @Expose val id: Int,
    @SerializedName("name") @Expose val name: String,
    @SerializedName("origin_country") @Expose val originCountry: List<String>,
    @SerializedName("original_language") @Expose val originalLanguage: String,
    @SerializedName("original_name") @Expose val originalName: String,
    @SerializedName("overview") @Expose val overview: String,
    @SerializedName("popularity") @Expose val popularity: Double,
    @SerializedName("poster_path") @Expose val posterPath: String,
    @SerializedName("vote_average") @Expose val voteAverage: Double,
    @SerializedName("vote_count") @Expose val voteCount: Int
)

data class PersonCrewDto(
    @SerializedName("backdrop_path") @Expose val backdropPath: String,
    @SerializedName("credit_id") @Expose val creditId: String,
    @SerializedName("department") @Expose val department: String,
    @SerializedName("episode_count") @Expose val episodeCount: Int,
    @SerializedName("first_air_date") @Expose val firstAirDate: String,
    @SerializedName("genre_ids") @Expose val genreIds: List<Int>,
    @SerializedName("id") @Expose val id: Int,
    @SerializedName("job") @Expose val job: String,
    @SerializedName("name") @Expose val name: String,
    @SerializedName("origin_country") @Expose val originCountry: List<String>,
    @SerializedName("original_language") @Expose val originalLanguage: String,
    @SerializedName("original_name") @Expose val originalName: String,
    @SerializedName("overview") @Expose val overview: String,
    @SerializedName("popularity") @Expose val popularity: Double,
    @SerializedName("poster_path") @Expose val posterPath: String,
    @SerializedName("vote_average") @Expose val voteAverage: Double,
    @SerializedName("vote_count") @Expose val voteCount: Int
)

fun PersonCreditDto.toCredit() : PersonCredit {
    return this.let {
        PersonCredit(
            cast = map(it.cast),
            crew = map(it.crew),
            id = it.id
        )
    }
}

@JvmName("mapCast")
fun map(cast: List<PersonCastDto>) : List<PersonCast> {
    return cast.map {
        PersonCast(
            backdropPath = it.backdropPath,
            character = it.character,
            creditId = it.creditId,
            episodeCount = it.episodeCount,
            firstAirDate = it.firstAirDate,
            genreIds = it.genreIds,
            id = it.id,
            name = it.name,
            originCountry = it.originCountry,
            originalLanguage = it.originalLanguage,
            originalName = it.originalName,
            overview = it.overview,
            popularity = it.popularity,
            posterPath = it.posterPath,
            voteAverage = it.voteAverage,
            voteCount = it.voteCount
        )
    }
}

@JvmName("mapCrew")
fun map(crew: List<PersonCrewDto>) : List<PersonCrew> {
    return crew.map {
        PersonCrew(
            backdropPath = it.backdropPath,
            creditId = it.creditId,
            department = it.department,
            episodeCount = it.episodeCount,
            firstAirDate = it.firstAirDate,
            genreIds = it.genreIds,
            id = it.id,
            job = it.job,
            name = it.name,
            originCountry = it.originCountry,
            originalLanguage = it.originalLanguage,
            originalName = it.originalName,
            overview = it.overview,
            popularity = it.popularity,
            posterPath = it.posterPath,
            voteAverage = it.voteAverage,
            voteCount = it.voteCount
        )
    }
}