package com.example.batmanproject.api

import com.example.batmanproject.model.Films
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface ApiService {

     @GET("/")
     suspend fun getFilms(
          @Query("apikey") apiKey: String = "3e974fca",
          @Query("s") searchTerm: String = "batman"
     ): Response<Films>
}