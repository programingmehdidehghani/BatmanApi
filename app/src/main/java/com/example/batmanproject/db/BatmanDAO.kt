package com.example.batmanproject.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.batmanproject.model.DetailFilm
import com.example.batmanproject.model.Search

@Dao
interface BatmanDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFilms(article: List<Search>)

    @Query("SELECT * FROM films")
    fun getAllFilmsDB() : LiveData<List<Search>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDetailFilm(article: DetailFilm)

    @Query("SELECT * FROM detailFilm WHERE imdbID = :imdbID")
    fun getDetailFilmDB(imdbID: String) : LiveData<DetailFilm>


}