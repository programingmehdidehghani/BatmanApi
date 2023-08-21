package com.example.batmanproject.ui.DetailMovie

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.batmanproject.model.DetailFilm
import com.example.batmanproject.model.Films
import com.example.batmanproject.model.Search
import com.example.batmanproject.repository.DetailFilmRepository
import com.example.batmanproject.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject


@HiltViewModel
class DetailFilmViewModel @Inject constructor(
    private val repository: DetailFilmRepository
): ViewModel() {

    private val _getDetailFilm = MutableLiveData<Resource<DetailFilm>>()
    val getDetailFilm: LiveData<Resource<DetailFilm>> = _getDetailFilm


    fun detailFilm(imdbID: String) = viewModelScope.launch (Dispatchers.IO) {
        resultDetailFilms(imdbID = imdbID)
    }


    private suspend fun resultDetailFilms(imdbID: String){
        _getDetailFilm.postValue(Resource.Loading)
        try {
            val response = repository.getDetailFilms(imdbID = imdbID)
            if (response.isSuccessful) {
                Log.i("Films", "success is  ${response.body()!!}")
                _getDetailFilm.postValue(Resource.Success(response.body()!!))
                repository.insert(response.body()!!.Search)
            }
        } catch (e: HttpException) {
            Log.i("Films","error is exception ${e.message()}")
            _getDetailFilm.postValue(Resource.Error(e.message()))
        } catch (t: Throwable) {
            when (t) {
                is IOException -> {
                    Log.i("Films","error is exception  ${t.message}")
                    _getDetailFilm.postValue(Resource.Error(t.message!!))
                }
            }
        }
    }


    fun getFilmsDB() : LiveData<List<Search>> {
        return repository.getFilmsDB()
    }
}