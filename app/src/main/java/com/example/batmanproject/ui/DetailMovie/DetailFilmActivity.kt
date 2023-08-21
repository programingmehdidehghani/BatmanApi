package com.example.batmanproject.ui.DetailMovie

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.batmanproject.R
import com.example.batmanproject.adapter.FilmsAdapter
import com.example.batmanproject.adapter.RateDetailFilmAdapter
import com.example.batmanproject.databinding.ActivityDetailFilmBinding
import com.example.batmanproject.databinding.ActivityFilmsBinding
import com.example.batmanproject.model.DetailFilm
import com.example.batmanproject.ui.Films.FilmViewModel
import com.example.batmanproject.util.ImageLoader
import com.example.batmanproject.util.InternetUtils
import com.example.batmanproject.util.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFilmActivity : AppCompatActivity() {

    private val detailFilmViewModel: DetailFilmViewModel by viewModels()

    var imdbID : String = ""

    private var rateDetailFilmAdapter = RateDetailFilmAdapter()



    private var _binding: ActivityDetailFilmBinding? = null
    private val viewBinding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityDetailFilmBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        imdbID = intent.getStringExtra("imdbID").toString()
        setUpFilmsRecyclerView()
        if (InternetUtils.isInternetAvailable(this)){
            detailFilmViewModel.detailFilm(imdbID)
            detailFilmViewModel.getDetailFilm.observe(this, Observer { response ->
                when (response) {
                    is Resource.Success -> {
                        hideProgress()
                        setDataOnView(response.data.Title,response.data.Year,response.data.Rated,response.data.Released
                        ,response.data.BoxOffice,response.data.Actors,response.data.Country,response.data.Director,
                        response.data.Genre,response.data.Language,response.data.Plot,response.data.Runtime,
                        response.data.Type,response.data.Website,response.data.Writer,response.data.Ratings,response.data.Poster)
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
            detailFilmViewModel.getDetailFilmsDB(imdbID).observe(this, Observer { filmDB ->
                Log.i("internet","is off")
                hideProgress()
                if (filmDB != null){
                    setDataOnView(filmDB.Title,filmDB.Year,filmDB.Rated,filmDB.Released
                        ,filmDB.BoxOffice,filmDB.Actors,filmDB.Country,filmDB.Director,
                        filmDB.Genre,filmDB.Language,filmDB.Plot,filmDB.Runtime,
                        filmDB.Type,filmDB.Website,filmDB.Writer,filmDB.Ratings,filmDB.Poster)
                }else {
                    Toast.makeText(
                        applicationContext,
                        "there is no data please turn on your internet", Toast.LENGTH_SHORT
                    ).show()
                }

            })
        }


    }

    private fun setDataOnView(title: String,year: String
                              ,rated: String,release: String,boxOffice: String,actors: String
    ,country: String,director: String,genre: String,language: String,plot: String,
    runTime: String,type: String,webSite: String,writers: String,rating: List<DetailFilm.Rating>,poster: String){
        viewBinding.txtTitleInDetailActivity.text = title
        viewBinding.txtYearInDetailActivity.text = year
        viewBinding.txtRatedInDetailActivity.text = rated
        viewBinding.txtReleasedInDetailActivity.text = release
        viewBinding.txtBoxOfficeInDetailActivity.text = boxOffice
        viewBinding.txtActorsInDetailActivity.text = actors
        viewBinding.txtCountryInDetailActivity.text = country
        viewBinding.txtDirectorInDetailActivity.text = director
        viewBinding.txtGenreInDetailActivity.text = genre
        viewBinding.txtLanguageInDetailActivity.text = language
        viewBinding.txtPlotInDetailActivity.text = plot
        viewBinding.txtRunTimeInDetailActivity.text = runTime
        viewBinding.txtTypeInDetailActivity.text = type
        viewBinding.txtWebSiteInDetailActivity.text = webSite
        viewBinding.txtWritersInDetailActivity.text = writers
        ImageLoader.loadImage(
            viewBinding.ivPosterInDetailActivity,
            poster
        )
        rateDetailFilmAdapter.updateList(rating)
    }

    private fun setUpFilmsRecyclerView(){
        var layoutManager
                = LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false)
        viewBinding.rvRatingInDetailActivity.apply {
            this.layoutManager = layoutManager
            adapter = rateDetailFilmAdapter
        }
    }


    private fun showProgress() {
        viewBinding.progressInDetailFilmActivity.visibility = View.VISIBLE
        viewBinding.rvRatingInDetailActivity.visibility = View.GONE
    }

    private fun hideProgress() {
        viewBinding.progressInDetailFilmActivity.visibility = View.GONE
        viewBinding.rvRatingInDetailActivity.visibility = View.VISIBLE
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null;
    }
}