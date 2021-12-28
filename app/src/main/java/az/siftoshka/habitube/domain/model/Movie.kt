package az.siftoshka.habitube.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import az.siftoshka.habitube.domain.util.Constants
import java.util.*

@Entity(tableName = Constants.MOVIE_TABLE)
data class Movie(
    @ColumnInfo(name = "adult") var adult: Boolean? = null,
    @Ignore @ColumnInfo(name = "backdrop_path") var backdropPath: String? = null,
    @Ignore var genres: List<MovieGenre>? = emptyList(),
    @PrimaryKey @ColumnInfo(name = "id") var id: Int? = null,
    @ColumnInfo(name = "imdb_id") var imdbId: String? = null,
    @ColumnInfo(name = "original_title") var originalTitle: String? = null,
    @ColumnInfo(name = "overview") var overview: String? = null,
    @ColumnInfo(name = "popularity") var popularity: Double? = null,
    @ColumnInfo(name = "poster_path") var posterPath: String? = null,
    @ColumnInfo(name = "release_date") var releaseDate: String? = null,
    @ColumnInfo(name = "runtime") var runtime: Int? = null,
    @ColumnInfo(name = "status") var status: String? = null,
    @ColumnInfo(name = "title") var title: String? = null,
    @ColumnInfo(name = "vote_average") var voteAverage: Double? = null,
    @ColumnInfo(name = "vote_count") var voteCount: Int? = null,
    @ColumnInfo(name = "budget") var budget: Long? = null,
    @ColumnInfo(name = "revenue") var revenue: Long? = null,
    @ColumnInfo(name = "added_date") var addedDate: Date? = null,
    @ColumnInfo(name = "my_rating") var myRating: Float? = null,
    @Ignore var homepage: String? = null,
    @Ignore var originalLanguage: String? = null,
    @Ignore var productionCompanies: List<ProductionCompany>? = emptyList(),
    @Ignore var productionCountries: List<ProductionCountry>? = emptyList(),
    @Ignore var spokenLanguages: List<SpokenLanguage>? = emptyList(),
    @Ignore var tagline: String? = null,
    @Ignore var video: Boolean? = false
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