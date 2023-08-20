package com.example.batmanproject.ui.Films

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.batmanproject.repository.FilmsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class FilmViewModel @Inject constructor(
    private val repository: FilmsRepository,
    application: Application
) : AndroidViewModel(application){

    
}