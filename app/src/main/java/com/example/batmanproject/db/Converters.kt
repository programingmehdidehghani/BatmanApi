package com.example.batmanproject.db

import androidx.room.TypeConverter
import com.example.batmanproject.model.DetailFilm
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {

    private val gson = Gson()

    @TypeConverter
    fun fromRatingList(ratings: List<DetailFilm.Rating>?): String? {
        return gson.toJson(ratings)
    }

    @TypeConverter
    fun toRatingList(ratingsJson: String?): List<DetailFilm.Rating>? {
        val type = object : TypeToken<List<DetailFilm.Rating>>() {}.type
        return gson.fromJson(ratingsJson, type)
    }

}