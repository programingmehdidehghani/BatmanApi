package com.example.batmanproject.ui.Films

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.batmanproject.MyApp
import com.example.batmanproject.R
import com.example.batmanproject.adapter.FilmsAdapter
import com.example.batmanproject.adapter.OnItemClickCallback
import com.example.batmanproject.databinding.ActivityFilmsBinding
import com.example.batmanproject.ui.DetailMovie.DetailFilmActivity
import com.example.batmanproject.util.Constant
import com.example.batmanproject.util.InternetUtils
import com.example.batmanproject.util.Resource
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class FilmsActivity : AppCompatActivity() , OnItemClickCallback {

    private val filmViewModel: FilmViewModel by viewModels()

    private var filmsAdapter = FilmsAdapter(this)


    private var _binding: ActivityFilmsBinding? = null
    private val viewBinding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityFilmsBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        setUpFilmsRecyclerView()
        if (InternetUtils.isInternetAvailable(this)){
            filmViewModel.films()
            filmViewModel.getFilms.observe(this,Observer { response ->
                when (response) {
                    is Resource.Success -> {
                        Log.i("internet","is on")
                        filmsAdapter.updateList(response.data.Search)
                        hideProgress()
                    }
                    is Resource.Error -> {
                        Toast.makeText(
                            applicationContext,
                            response.errorMessage, Toast.LENGTH_SHORT
                        ).show()
                        hideProgress()
                    }
                    is Resource.Loading -> {
                        showProgress()
                    }
                }
            })
        } else {
            filmViewModel.getFilmsDB().observe(this, Observer { filmDB ->
                Log.i("internet","is off")
                if (filmDB.isNotEmpty()){
                    filmsAdapter.updateList(filmDB)
                } else {
                    Toast.makeText(
                        applicationContext,
                        "there is no data please turn on your internet", Toast.LENGTH_SHORT
                    ).show()
                }
                hideProgress()
            })
        }
    }


    private fun showProgress() {
        viewBinding.progressInActivity.visibility = View.VISIBLE
        viewBinding.rvFilmsInActivityFilms.visibility = View.GONE
    }

    private fun hideProgress() {
        viewBinding.progressInActivity.visibility = View.GONE
        viewBinding.rvFilmsInActivityFilms.visibility = View.VISIBLE
    }


    private fun setUpFilmsRecyclerView(){
        var layoutManager
                = LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false)
        viewBinding.rvFilmsInActivityFilms.apply {
            this.layoutManager = layoutManager
            adapter = filmsAdapter
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null;
    }

    override fun onItemClick(imdbID: String) {
        val intent = Intent(this@FilmsActivity, DetailFilmActivity::class.java)
        intent.putExtra("imdbID",imdbID)
        startActivity(intent)
    }
}