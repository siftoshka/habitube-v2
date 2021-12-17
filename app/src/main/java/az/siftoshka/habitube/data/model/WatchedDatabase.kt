package az.siftoshka.habitube.data.model

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import az.siftoshka.habitube.domain.model.Movie
import az.siftoshka.habitube.domain.model.TvShow
import az.siftoshka.habitube.domain.util.DateConverters
import az.siftoshka.habitube.domain.util.IntegerConverter

/**
 * The abstract class of database.
 */
@Database(entities = [Movie::class, TvShow::class], exportSchema = false, version = 1)
@TypeConverters(DateConverters::class, IntegerConverter::class)
abstract class WatchedDatabase : RoomDatabase() {

    abstract fun getMovieDAO(): MovieDAO

    abstract fun getShowDAO(): ShowDAO
}