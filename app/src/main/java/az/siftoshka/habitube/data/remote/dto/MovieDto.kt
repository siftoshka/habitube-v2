package az.siftoshka.habitube.data.remote.dto

import az.siftoshka.habitube.domain.model.*
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class MovieDto(
    @SerializedName("adult") @Expose val adult: Boolean,
    @SerializedName("backdrop_path") @Expose val backdropPath: String,
    @SerializedName("budget") @Expose val budget: Long,
    @SerializedName("genres") @Expose val genres: List<MovieGenreDto>,
    @SerializedName("homepage") @Expose val homepage: String,
    @SerializedName("id") @Expose val id: Int,
    @SerializedName("imdb_id") @Expose val imdbId: String,
    @SerializedName("original_language") @Expose val originalLanguage: String,
    @SerializedName("original_title") @Expose val originalTitle: String,
    @SerializedName("overview") @Expose val overview: String,
    @SerializedName("popularity") @Expose val popularity: Double,
    @SerializedName("poster_path") @Expose val posterPath: String,
    @SerializedName("production_companies") @Expose val productionCompanies: List<ProductionCompanyDto>,
    @SerializedName("production_countries") @Expose val productionCountries: List<ProductionCountryDto>,
    @SerializedName("release_date") @Expose val releaseDate: String,
    @SerializedName("revenue") @Expose val revenue: Long,
    @SerializedName("runtime") @Expose val runtime: Int,
    @SerializedName("spoken_languages") @Expose val spokenLanguages: List<SpokenLanguageDto>,
    @SerializedName("status") @Expose val status: String,
    @SerializedName("tagline") @Expose val tagline: String,
    @SerializedName("title") @Expose val title: String,
    @SerializedName("video") @Expose val video: Boolean,
    @SerializedName("vote_average") @Expose val voteAverage: Double,
    @SerializedName("vote_count") @Expose val voteCount: Int
)

data class MovieGenreDto(
    @SerializedName("id") @Expose val id: Int,
    @SerializedName("name") @Expose val name: String
)

data class ProductionCompanyDto(
    @SerializedName("id") @Expose val id: Int,
    @SerializedName("logo_path") @Expose val logoPath: String,
    @SerializedName("name") @Expose val name: String,
    @SerializedName("origin_country") @Expose val originCountry: String
)

data class ProductionCountryDto(
    @SerializedName("iso_3166_1") @Expose val iso31661: String,
    @SerializedName("name") @Expose val name: String
)

data class SpokenLanguageDto(
    @SerializedName("english_name")
    @Expose val englishName: String,
    @SerializedName("iso_639_1")
    @Expose val iso6391: String,
    @SerializedName("name")
    @Expose val name: String
)

fun MovieDto.toMovie() : Movie {
    return this.let {
        Movie(
            adult = it.adult,
            backdropPath = it.backdropPath,
            budget = it.budget,
            genres = map(it.genres),
            homepage = it.homepage,
            id = it.id,
            imdbId = it.imdbId,
            originalLanguage = it.originalLanguage,
            originalTitle = it.originalTitle,
            overview = it.overview,
            popularity = it.popularity,
            posterPath = it.posterPath,
            productionCompanies = map(it.productionCompanies),
            productionCountries = map(it.productionCountries),
            releaseDate = it.releaseDate,
            revenue = it.revenue,
            runtime = it.runtime,
            spokenLanguages = map(it.spokenLanguages),
            status = it.status,
            tagline = it.tagline,
            title = it.title,
            video = it.video,
            voteAverage = it.voteAverage,
            voteCount = it.voteCount
        )
    }
}

@JvmName("mapGenres")
fun map(genres : List<MovieGenreDto>) : List<MovieGenre> {
    return genres.map {
        MovieGenre(
            id = it.id,
            name = it.name
        )
    }
}

@JvmName("mapProductionCompanies")
fun map(productionCompanies: List<ProductionCompanyDto>) : List<ProductionCompany> {
    return productionCompanies.map {
        ProductionCompany(
            id = it.id,
            logoPath = it.logoPath,
            name = it.name,
            originCountry = it.originCountry
        )
    }
}

@JvmName("mapProductionCountries")
fun map(productionCountries: List<ProductionCountryDto>) : List<ProductionCountry> {
    return productionCountries.map {
        ProductionCountry(
            iso31661 = it.iso31661,
            name = it.name,
        )
    }
}

@JvmName("mapSpokenLanguages")
fun map(spokenLanguages: List<SpokenLanguageDto>) : List<SpokenLanguage> {
    return spokenLanguages.map {
        SpokenLanguage(
            englishName = it.englishName,
            iso6391 = it.iso6391,
            name = it.name
        )
    }
}