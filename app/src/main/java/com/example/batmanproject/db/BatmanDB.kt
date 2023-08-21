package com.example.batmanproject.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.batmanproject.model.DetailFilm
import com.example.batmanproject.model.Search


@Database(
    entities = [Search::class,DetailFilm::class],
    version = 1
)
@TypeConverters(Converters::class)
abstract class BatmanDB : RoomDatabase() {

    abstract fun getRunDao() : BatmanDAO

}