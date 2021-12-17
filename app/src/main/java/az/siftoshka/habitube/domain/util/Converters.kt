package az.siftoshka.habitube.domain.util

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import java.util.*


/**
 *  General converters for Room.
 */
class DateConverters {

    @TypeConverter
    fun toDate(dateLong: Long?): Date? {
        return dateLong?.let { Date(it) }
    }

    @TypeConverter
    fun fromDate(date: Date?): Long? {
        return date?.time
    }
}

class IntegerConverter {
    private val gson = Gson()

    @TypeConverter
    fun stringToIntegerList(data: String?): List<Int?>? {
        if (data == null) return Collections.emptyList()
        val listType: Type = object : TypeToken<List<Int?>?>() {}.type
        return gson.fromJson(data, listType)
    }

    @TypeConverter
    fun integersListToString(integers: List<Int?>?): String? {
        return gson.toJson(integers)
    }
}