package com.example.batmanproject.repository

import com.example.batmanproject.api.ApiService
import javax.inject.Inject

class FilmsRepository @Inject constructor(
    private val apiService: ApiService
) {
    suspend fun getFilms() = apiService.getFilms()
}