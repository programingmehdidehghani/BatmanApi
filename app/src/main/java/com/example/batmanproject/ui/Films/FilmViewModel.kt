package com.example.batmanproject.ui.Films

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.batmanproject.MyApp
import com.example.batmanproject.R
import com.example.batmanproject.model.Films
import com.example.batmanproject.repository.FilmsRepository
import com.example.batmanproject.util.Resource
import com.example.batmanproject.util.hasInternetConnection
import com.example.batmanproject.util.toast
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject


@HiltViewModel
class FilmViewModel @Inject constructor(
    private val repository: FilmsRepository,
    application: Application
) : AndroidViewModel(application){

    private val _getFilms = MutableLiveData<Resource<Films>>()
    val getFilms: LiveData<Resource<Films>> = _getFilms


    fun films() = viewModelScope.launch {
        resultFilms()
    }


    private suspend fun resultFilms(){
        _getFilms.postValue(Resource.Loading)
        try {
            if (hasInternetConnection<MyApp>()) {
                val response = repository.getFilms()
                if (response.isSuccessful) {
                    Log.i("Constant","error is  ${response.body()!!}")
                    _getFilms.postValue(Resource.Success(response.body()!!))
                }
            } else {
                _getFilms.postValue(Resource.Error("No Internet Connection.!"))
            }
        } catch (e: HttpException) {
            Log.i("Constant","error is exception ${e.message()}")
            _getFilms.postValue(Resource.Error(e.message()))
        } catch (t: Throwable) {
            when (t) {
                is IOException -> {
                    Log.i("Constant","error is exception  ${t.message}")
                    _getFilms.postValue(Resource.Error(t.message!!))
                }
            }
        }
    }




}