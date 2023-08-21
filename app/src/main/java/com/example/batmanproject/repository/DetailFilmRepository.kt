package com.example.batmanproject.repository

import com.example.batmanproject.api.ApiService
import com.example.batmanproject.model.Search
import javax.inject.Inject

class DetailFilmRepository @Inject constructor(
    private val apiService: ApiService
) {

    suspend fun getDetailFilms(imdbID: String) = apiService.getFilmDetails(imdbID = imdbID)


    suspend fun insert (search: List<Search>) = db.getRunDao().insertFilms(search)

    fun getFilmsDB () = db.getRunDao().getAllFilmsDB()

}