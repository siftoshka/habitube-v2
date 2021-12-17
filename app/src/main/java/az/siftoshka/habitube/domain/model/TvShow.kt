package az.siftoshka.habitube.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import az.siftoshka.habitube.domain.util.Constants
import java.util.*

@Entity(tableName = Constants.SHOW_TABLE)
data class TvShow(
    @Ignore var backdropPath: String? = null,
    @Ignore var episodeRunTime: List<Int>? = emptyList(),
    @ColumnInfo(name = "first_air_date") var firstAirDate: String? = null,
    @Ignore var genres: List<TvShowGenre>? = emptyList(),
    @Ignore var homepage: String? = null,
    @PrimaryKey var id: Int? = 0,
    @ColumnInfo(name = "in_production") var inProduction: Boolean? = false,
    @ColumnInfo(name = "last_air_date") var lastAirDate: String? = null,
    @ColumnInfo(name = "name") var name: String? = null,
    @Ignore var networks: List<Network>? = emptyList(),
    @ColumnInfo(name = "number_of_episodes") var numberOfEpisodes: Int? = 0,
    @ColumnInfo(name = "number_of_seasons") var numberOfSeasons: Int? = 0,
    @Ignore var originCountry: List<String>? = emptyList(),
    @Ignore var originalLanguage: String? = null,
    @Ignore var originalName: String? = null,
    @ColumnInfo(name = "overview") var overview: String? = null,
    @ColumnInfo(name = "popularity") var popularity: Double? = 0.0,
    @ColumnInfo(name = "poster_path") var posterPath: String? = null,
    @Ignore var seasons: List<Season>? = emptyList(),
    @ColumnInfo(name = "status") var status: String? = null,
    @Ignore var tagline: String? = null,
    @Ignore var type: String? = null,
    @ColumnInfo(name = "vote_average") var voteAverage: Double? = 0.0,
    @ColumnInfo(name = "vote_count") var voteCount: Int? = 0,
    @ColumnInfo(name = "added_date") var addedDate: Date? = null,
    @ColumnInfo(name = "my_rating") var myRating: Float? = 0f,
    @ColumnInfo(name = "watched_seasons") var watchedSeasons: List<Int>? = emptyList()
)

data class TvShowGenre(
    val id: Int? = 0,
    val name: String? = null
)

data class Network(
    val id: Int? = 0,
    val logoPath: String? = null,
    val name: String? = null,
    val originCountry: String? = null
)

data class Season(
    val airDate: String? = null,
    val episodeCount: Int? = 0,
    val id: Int? = 0,
    val name: String? = null,
    val overview: String? = null,
    val posterPath: String? = null,
    val seasonNumber: Int? = 0
)