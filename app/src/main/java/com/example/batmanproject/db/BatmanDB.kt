package com.example.batmanproject.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.batmanproject.model.DetailFilm
import com.example.batmanproject.model.Search


@Database(
    entities = [Search::class,DetailFilm::class],
    version = 1
)
abstract class BatmanDB : RoomDatabase() {

    abstract fun getRunDao() : BatmanDAO

}