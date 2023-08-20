package com.example.batmanproject.repository

import com.example.batmanproject.api.ApiService
import com.example.batmanproject.db.BatmanDB
import com.example.batmanproject.model.Search
import javax.inject.Inject

class FilmsRepository @Inject constructor(
    private val apiService: ApiService,
    private val db: BatmanDB
) {
    suspend fun getFilms() = apiService.getFilms()

    suspend fun insert (search: List<Search>) = db.getRunDao().insertArticle(search)

}