package az.siftoshka.habitube.data.remote.dto

import az.siftoshka.habitube.domain.model.Omdb
import az.siftoshka.habitube.domain.model.OmdbRating
import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose

data class OmdbDto(
    @SerializedName("Actors") @Expose val actors: String,
    @SerializedName("Awards") @Expose val awards: String,
    @SerializedName("Country") @Expose val country: String,
    @SerializedName("Director") @Expose val director: String,
    @SerializedName("Genre") @Expose val genre: String,
    @SerializedName("imdbID") @Expose val imdbID: String,
    @SerializedName("imdbRating") @Expose val imdbRating: String,
    @SerializedName("imdbVotes") @Expose val imdbVotes: String,
    @SerializedName("Language") @Expose val language: String,
    @SerializedName("Metascore") @Expose val metascore: String,
    @SerializedName("Plot") @Expose val plot: String,
    @SerializedName("Poster") @Expose val poster: String,
    @SerializedName("Rated") @Expose val rated: String,
    @SerializedName("Ratings") @Expose val ratings: List<OmdbRatingDto>,
    @SerializedName("Released") @Expose val released: String,
    @SerializedName("Response") @Expose val response: String,
    @SerializedName("Runtime") @Expose val runtime: String,
    @SerializedName("Title") @Expose val title: String,
    @SerializedName("totalSeasons") @Expose val totalSeasons: String,
    @SerializedName("Type") @Expose val type: String,
    @SerializedName("Writer") @Expose val writer: String,
    @SerializedName("Year") @Expose val year: String,
    @SerializedName("BoxOffice") @Expose val boxOffice: String,
    @SerializedName("DVD") @Expose val dVD: String,
    @SerializedName("Production") @Expose val production: String,
    @SerializedName("Website") @Expose val website: String
)

data class OmdbRatingDto(
    @SerializedName("Source") @Expose val source: String,
    @SerializedName("Value") @Expose val value: String
)

fun OmdbDto.toData() : Omdb {
    return this.let {
        Omdb(
            actors = it.actors,
            awards = it.awards,
            country = it.country,
            director = it.director,
            genre = it.genre,
            imdbID = it.imdbID,
            imdbRating = it.imdbRating,
            imdbVotes = it.imdbVotes,
            language = it.language,
            metascore = it.metascore,
            plot = it.plot,
            poster = it.poster,
            rated = it.rated,
            ratings = map(it.ratings),
            released = it.released,
            response = it.response,
            runtime = it.runtime,
            title = it.title,
            totalSeasons = it.totalSeasons,
            type = it.type,
            writer = it.writer,
            year = it.year,
            boxOffice = it.boxOffice,
            dVD = it.dVD,
            production = it.production,
            website = it.website
        )
    }
}

fun map(ratings: List<OmdbRatingDto>?) : List<OmdbRating>? {
    return ratings?.map {
        OmdbRating(
            source = it.source,
            value = it.value
        )
    }
}