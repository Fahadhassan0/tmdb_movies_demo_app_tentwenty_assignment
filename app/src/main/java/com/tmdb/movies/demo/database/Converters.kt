package com.tmdb.movies.demo.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

/**
 * Type converters to allow Room to reference complex data types.
 */
class Converters {

    @TypeConverter
    fun stringToGenreIds(genre_ids: String?): List<Int?>? {
        val listType: Type = object : TypeToken<List<Int?>?>() {}.type
        return Gson().fromJson(genre_ids, listType)
    }

    @TypeConverter
    fun genreIdsToString(genreIds: List<Int?>?): String? {
        val gson = Gson()
        return gson.toJson(genreIds)
    }
}