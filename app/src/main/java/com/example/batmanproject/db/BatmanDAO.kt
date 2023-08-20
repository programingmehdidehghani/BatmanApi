package com.example.batmanproject.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.batmanproject.model.Search

@Dao
interface BatmanDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArticle(article: List<Search>)

    @Query("SELECT * FROM films")
    fun getAllFilmsDB() : LiveData<List<Search>>
}