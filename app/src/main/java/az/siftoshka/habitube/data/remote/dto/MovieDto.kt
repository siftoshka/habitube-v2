package az.siftoshka.habitube.data.remote.dto


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class MovieDto(
    @SerializedName("adult") @Expose val adult: Boolean,
    @SerializedName("backdrop_path") @Expose val backdropPath: String,
    @SerializedName("budget") @Expose val budget: Int,
    @SerializedName("genres") @Expose val genres: List<MovieGenre>,
    @SerializedName("homepage") @Expose val homepage: String,
    @SerializedName("id") @Expose val id: Int,
    @SerializedName("imdb_id") @Expose val imdbId: String,
    @SerializedName("original_language") @Expose val originalLanguage: String,
    @SerializedName("original_title") @Expose val originalTitle: String,
    @SerializedName("overview") @Expose val overview: String,
    @SerializedName("popularity") @Expose val popularity: Double,
    @SerializedName("poster_path") @Expose val posterPath: String,
    @SerializedName("production_companies") @Expose val productionCompanies: List<ProductionCompany>,
    @SerializedName("production_countries") @Expose val productionCountries: List<ProductionCountry>,
    @SerializedName("release_date") @Expose val releaseDate: String,
    @SerializedName("revenue") @Expose val revenue: Int,
    @SerializedName("runtime") @Expose val runtime: Int,
    @SerializedName("spoken_languages") @Expose val spokenLanguages: List<SpokenLanguage>,
    @SerializedName("status") @Expose val status: String,
    @SerializedName("tagline") @Expose val tagline: String,
    @SerializedName("title") @Expose val title: String,
    @SerializedName("video") @Expose val video: Boolean,
    @SerializedName("vote_average") @Expose val voteAverage: Double,
    @SerializedName("vote_count") @Expose val voteCount: Int
)

data class MovieGenre(
    @SerializedName("id") @Expose val id: Int,
    @SerializedName("name") @Expose val name: String
)

data class ProductionCompany(
    @SerializedName("id") @Expose val id: Int,
    @SerializedName("logo_path") @Expose val logoPath: String,
    @SerializedName("name") @Expose val name: String,
    @SerializedName("origin_country") @Expose val originCountry: String
)

data class ProductionCountry(
    @SerializedName("iso_3166_1") @Expose val iso31661: String,
    @SerializedName("name") @Expose val name: String
)

data class SpokenLanguage(
    @SerializedName("english_name")
    @Expose val englishName: String,
    @SerializedName("iso_639_1")
    @Expose val iso6391: String,
    @SerializedName("name")
    @Expose val name: String
)