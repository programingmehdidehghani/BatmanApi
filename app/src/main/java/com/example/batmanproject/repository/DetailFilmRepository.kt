package com.example.batmanproject.repository

import com.example.batmanproject.api.ApiService
import com.example.batmanproject.db.BatmanDB
import com.example.batmanproject.model.DetailFilm
import com.example.batmanproject.model.Search
import javax.inject.Inject

class DetailFilmRepository @Inject constructor(
    private val apiService: ApiService,
    private val db: BatmanDB
) {

    suspend fun getDetailFilms(imdbID: String) = apiService.getFilmDetails(imdbID = imdbID)

    suspend fun insertDetailFilm (detailFilm: DetailFilm) = db.getRunDao().insertDetailFilm(detailFilm)

    fun getDetailFilmsDB (imdbID: String) = db.getRunDao().getDetailFilmDB(imdbID)

}