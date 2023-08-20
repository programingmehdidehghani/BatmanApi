package com.example.batmanproject.ui.Films

import android.app.Application
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.batmanproject.MyApp
import com.example.batmanproject.R
import com.example.batmanproject.model.Films
import com.example.batmanproject.model.Search
import com.example.batmanproject.repository.FilmsRepository
import com.example.batmanproject.util.Constant
import com.example.batmanproject.util.Resource
import com.example.batmanproject.util.hasInternetConnection
import com.example.batmanproject.util.toast
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject


@HiltViewModel
class FilmViewModel @Inject constructor(
    private val repository: FilmsRepository,
    application: Application,
    private val sharedPref: SharedPreferences
) : AndroidViewModel(application){

    private val _getFilms = MutableLiveData<Resource<Films>>()
    val getFilms: LiveData<Resource<Films>> = _getFilms


    fun films() = viewModelScope.launch (Dispatchers.IO) {
        resultFilms()
    }


    private suspend fun resultFilms(){
        _getFilms.postValue(Resource.Loading)
        try {
            if (hasInternetConnection<MyApp>()) {
                val response = repository.getFilms()
                if (response.isSuccessful) {
                    Log.i("Films","success is  ${response.body()!!}")
                    _getFilms.postValue(Resource.Success(response.body()!!))
                    repository.insert(response.body()!!.Search)
                    sharedPref.edit()
                        .putBoolean(Constant.IS_INTERNET, false)
                        .apply()
                }
            } else {
                // No Internet
                _getFilms.postValue(Resource.Error("No Internet"))
                sharedPref.edit()
                    .putBoolean(Constant.IS_INTERNET, true)
                    .apply()
                getFilmsDB()
            }
        } catch (e: HttpException) {
            Log.i("Films","error is exception ${e.message()}")
            _getFilms.postValue(Resource.Error(e.message()))
            sharedPref.edit()
                .putBoolean(Constant.IS_INTERNET, false)
                .apply()
        } catch (t: Throwable) {
            when (t) {
                is IOException -> {
                    Log.i("Films","error is exception  ${t.message}")
                    _getFilms.postValue(Resource.Error(t.message!!))
                    sharedPref.edit()
                        .putBoolean(Constant.IS_INTERNET, false)
                        .apply()
                }
            }
        }
    }


    fun getFilmsDB() : LiveData<List<Search>> {
        return repository.getFilmsDB()
    }




}