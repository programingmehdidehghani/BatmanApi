package com.example.batmanproject.api

import com.example.batmanproject.model.Films
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

     @GET("?apikey=3e974fca&s=batman")
     suspend fun getFilms() : Response<Films>
}