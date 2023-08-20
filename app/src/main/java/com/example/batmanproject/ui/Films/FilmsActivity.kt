package com.example.batmanproject.ui.Films

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.example.batmanproject.R
import com.example.batmanproject.databinding.ActivityFilmsBinding
import com.example.batmanproject.util.Resource
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class FilmsActivity : AppCompatActivity() {

    private val filmViewModel: FilmViewModel by viewModels()


    private var _binding: ActivityFilmsBinding? = null
    private val viewBinding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityFilmsBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        filmViewModel.films()
        filmViewModel.getFilms.observe(this,Observer { response ->
            when (response) {
                is Resource.Success -> {
                    hideProgress()
                }
                is Resource.Error -> {
                    hideProgress()
                }
                is Resource.Loading -> {
                    showProgress()
                }
            }
        })
    }


    private fun showProgress() {
        viewBinding.progressInActivity.visibility = View.VISIBLE
    }

    private fun hideProgress() {
        viewBinding.progressInActivity.visibility = View.GONE
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null;
    }
}