package com.example.batmanproject.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.example.batmanproject.model.Search
import retrofit2.http.Query

@Dao
interface BatmanDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArticle(article: List<Search>)
}