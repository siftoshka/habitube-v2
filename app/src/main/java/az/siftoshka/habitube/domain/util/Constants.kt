package az.siftoshka.habitube.domain.util

/**
 * The constant variables of the application.
 */
object Constants {

    // Info
    const val APP_VERSION = "2.0"

    // Database
    const val WATCHED = "watched"
    const val PLANNED = "planned"
    const val MOVIE_TABLE = "movies"
    const val SHOW_TABLE = "shows"

    // Shared Preferences
    const val PREFS_NAME = "app_prefs"

    // Firebase Realtime Database
    const val WATCHED_MOVIE = "movieWatched"
    const val WATCHED_SHOW = "showWatched"
    const val PLANNING_MOVIE = "moviePlanning"
    const val PLANNING_SHOW = "showPlanning"

    // API
    const val API_URL = "https://api.themoviedb.org/3/"
    const val OMDB_API_URL = "http://www.omdbapi.com/"
    const val IMAGE_URL_ORIGINAL = "https://image.tmdb.org/t/p/original"
    const val IMAGE_URL = "https://image.tmdb.org/t/p/w500"
    const val YOUTUBE_URL = "https://www.youtube.com/watch?v="
    const val VIMEO_URL = "https://vimeo.com/"
    const val PAGE_SIZE = 20

    // The Movie Database
    const val MOVIE_THEMOVIEDB = "https://www.themoviedb.org/movie/"
    const val TV_THEMOVIEDB = "https://www.themoviedb.org/tv/"
    const val PERSON_THEMOVIEDB = "https://www.themoviedb.org/person/"

    // About me
    const val DEV_TELEGRAM = "https://t.me/siftoshka"
    const val DEV_GITHUB = "https://github.com/siftoshka"
    const val DEV_INSTAGRAM = "https://www.instagram.com/siftoshka"
}