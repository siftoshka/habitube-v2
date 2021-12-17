package az.siftoshka.habitube.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import az.siftoshka.habitube.domain.util.Constants
import java.util.*

@Entity(tableName = Constants.MOVIE_TABLE)
data class Movie(
    @ColumnInfo(name = "adult") var adult: Boolean? = false,
    @Ignore @ColumnInfo(name = "backdrop_path") var backdropPath: String? = null,
    @ColumnInfo(name = "budget") var budget: Long? = 0,
    @Ignore var genres: List<MovieGenre>? = emptyList(),
    @Ignore var homepage: String? = null,
    @PrimaryKey @ColumnInfo(name = "id") var id: Int? = 0,
    @ColumnInfo(name = "imdb_id") var imdbId: String? = null,
    @Ignore var originalLanguage: String? = null,
    @ColumnInfo(name = "original_title") var originalTitle: String? = null,
    @ColumnInfo(name = "overview") var overview: String? = null,
    @ColumnInfo(name = "popularity") var popularity: Double? = 0.0,
    @ColumnInfo(name = "poster_path") var posterPath: String? = null,
    @Ignore var productionCompanies: List<ProductionCompany>? = emptyList(),
    @Ignore var productionCountries: List<ProductionCountry>? = emptyList(),
    @ColumnInfo(name = "release_date") var releaseDate: String? = null,
    @ColumnInfo(name = "revenue") var revenue: Long? = 0,
    @ColumnInfo(name = "runtime") var runtime: Int? = 0,
    @Ignore var spokenLanguages: List<SpokenLanguage>? = emptyList(),
    @ColumnInfo(name = "status") var status: String? = null,
    @Ignore var tagline: String? = null,
    @ColumnInfo(name = "title") var title: String? = null,
    @Ignore var video: Boolean? = false,
    @ColumnInfo(name = "vote_average")var voteAverage: Double? = 0.0,
    @ColumnInfo(name = "vote_count") var voteCount: Int? = 0,
    @ColumnInfo(name = "added_date") var addedDate: Date? = null,
    @ColumnInfo(name = "my_rating") var myRating: Float? = 0f
)

data class MovieGenre(
    val id: Int? = 0,
    val name: String? = null
)

data class ProductionCompany(
    val id: Int? = 0,
    val logoPath: String? = null,
    val name: String? = null,
    val originCountry: String? = null
)

data class ProductionCountry(
    val iso31661: String? = null,
    val name: String? = null
)

data class SpokenLanguage(
    val englishName: String? = null,
    val iso6391: String? = null,
    val name: String? = null
)