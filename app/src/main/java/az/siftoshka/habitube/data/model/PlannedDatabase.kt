package az.siftoshka.habitube.data.model

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import az.siftoshka.habitube.domain.model.Movie
import az.siftoshka.habitube.domain.model.TvShow
import az.siftoshka.habitube.domain.util.Constants
import az.siftoshka.habitube.domain.util.DateConverters
import az.siftoshka.habitube.domain.util.IntegerConverter

/**
 * The abstract class of database.
 */
@Database(entities = [Movie::class, TvShow::class], exportSchema = false, version = 2)
@TypeConverters(DateConverters::class, IntegerConverter::class)
abstract class PlannedDatabase : RoomDatabase() {

    abstract fun getMovieDAO(): MovieDAO

    abstract fun getShowDAO(): ShowDAO
}

internal val PLANNED_MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("DROP TABLE IF EXISTS " + Constants.MOVIE_TABLE + "_old;")
        database.execSQL("DROP TABLE IF EXISTS " + Constants.SHOW_TABLE + "_old;")
        database.execSQL("ALTER TABLE " + Constants.MOVIE_TABLE + " RENAME TO " + Constants.MOVIE_TABLE + "_old;")
        database.execSQL("ALTER TABLE " + Constants.SHOW_TABLE + " RENAME TO " + Constants.SHOW_TABLE + "_old;")
        database.execSQL("CREATE TABLE IF NOT EXISTS `movies` (`adult` INTEGER, `id` INTEGER, `imdb_id` TEXT, `original_title` TEXT, `overview` TEXT, `popularity` REAL, `poster_path` TEXT, `release_date` TEXT, `runtime` INTEGER, `status` TEXT, `title` TEXT, `vote_average` REAL, `vote_count` INTEGER, `budget` INTEGER, `revenue` INTEGER, `added_date` INTEGER, `my_rating` REAL, PRIMARY KEY(`id`))")
        database.execSQL("CREATE TABLE IF NOT EXISTS `shows` (`first_air_date` TEXT, `id` INTEGER, `in_production` INTEGER, `last_air_date` TEXT, `name` TEXT, `number_of_episodes` INTEGER, `number_of_seasons` INTEGER, `overview` TEXT, `popularity` REAL, `poster_path` TEXT, `status` TEXT, `vote_average` REAL, `vote_count` INTEGER, `added_date` INTEGER, `my_rating` REAL, `watched_seasons` TEXT, PRIMARY KEY(`id`))")
        database.execSQL("INSERT INTO " + Constants.MOVIE_TABLE + " SELECT * FROM " + Constants.MOVIE_TABLE + "_old;")
        database.execSQL("INSERT INTO " + Constants.SHOW_TABLE + " SELECT * FROM " + Constants.SHOW_TABLE + "_old;")
        database.execSQL("DROP TABLE IF EXISTS " + Constants.MOVIE_TABLE + "_old;")
        database.execSQL("DROP TABLE IF EXISTS " + Constants.SHOW_TABLE + "_old;")
    }
}