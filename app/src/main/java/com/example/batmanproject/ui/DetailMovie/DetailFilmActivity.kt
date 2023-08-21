package com.example.batmanproject.ui.DetailMovie

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.example.batmanproject.R
import com.example.batmanproject.databinding.ActivityDetailFilmBinding
import com.example.batmanproject.databinding.ActivityFilmsBinding
import com.example.batmanproject.ui.Films.FilmViewModel
import com.example.batmanproject.util.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFilmActivity : AppCompatActivity() {

    private val detailFilmViewModel: DetailFilmViewModel by viewModels()

    var imdbID : String = ""


    private var _binding: ActivityDetailFilmBinding? = null
    private val viewBinding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityDetailFilmBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        imdbID = intent.getStringExtra("imdbID").toString()
        detailFilmViewModel.detailFilm(imdbID)
        detailFilmViewModel.getDetailFilm.observe(this, Observer { response ->
            when (response) {
                is Resource.Success -> {
                }
                is Resource.Error -> {
                    Toast.makeText(
                        applicationContext,
                        response.errorMessage, Toast.LENGTH_SHORT
                    ).show()
                }
                is Resource.Loading -> {
                }
            }
        })

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null;
    }
}