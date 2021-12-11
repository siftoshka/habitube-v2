package az.siftoshka.habitube.data.remote.dto

import az.siftoshka.habitube.domain.model.Cast
import az.siftoshka.habitube.domain.model.Credit
import az.siftoshka.habitube.domain.model.Crew
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CreditDto(
    @SerializedName("cast") @Expose val cast: List<CastDto>,
    @SerializedName("crew") @Expose val crew: List<CrewDto>,
    @SerializedName("id") @Expose val id: Int
)

data class CastDto(
    @SerializedName("adult") @Expose val adult: Boolean,
    @SerializedName("cast_id") @Expose val castId: Int,
    @SerializedName("character") @Expose val character: String,
    @SerializedName("credit_id") @Expose val creditId: String,
    @SerializedName("gender") @Expose val gender: Int,
    @SerializedName("id") @Expose val id: Int,
    @SerializedName("known_for_department") @Expose val knownForDepartment: String,
    @SerializedName("name") @Expose val name: String,
    @SerializedName("order") @Expose val order: Int,
    @SerializedName("original_name") @Expose val originalName: String,
    @SerializedName("popularity") @Expose val popularity: Double,
    @SerializedName("profile_path") @Expose val profilePath: String
)

data class CrewDto(
    @SerializedName("adult") @Expose val adult: Boolean,
    @SerializedName("credit_id") @Expose val creditId: String,
    @SerializedName("department") @Expose val department: String,
    @SerializedName("gender") @Expose val gender: Int,
    @SerializedName("id") @Expose val id: Int,
    @SerializedName("job") @Expose val job: String,
    @SerializedName("known_for_department") @Expose val knownForDepartment: String,
    @SerializedName("name") @Expose val name: String,
    @SerializedName("original_name") @Expose val originalName: String,
    @SerializedName("popularity") @Expose val popularity: Double,
    @SerializedName("profile_path") @Expose val profilePath: String
)

fun CreditDto.toCredits() : Credit {
    return this.let {
        Credit(
            cast = map(it.cast),
            crew = map(it.crew),
            id = it.id
        )
    }
}

@JvmName("mapCast")
fun map(cast: List<CastDto>) : List<Cast> {
    return cast.map {
        Cast(
            adult = it.adult,
            castId = it.castId,
            character = it.character,
            creditId = it.creditId,
            gender = it.gender,
            id = it.id,
            knownForDepartment = it.knownForDepartment,
            name = it.name,
            order = it.order,
            originalName = it.originalName,
            popularity = it.popularity,
            profilePath = it.profilePath
        )
    }
}

@JvmName("mapCrew")
fun map(crew: List<CrewDto>) : List<Crew> {
    return crew.map {
        Crew(
            adult = it.adult,
            creditId = it.creditId,
            department = it.department,
            gender = it.gender,
            id = it.id,
            job = it.job,
            knownForDepartment = it.knownForDepartment,
            name = it.name,
            originalName = it.originalName,
            popularity = it.popularity,
            profilePath = it.profilePath
        )
    }
}