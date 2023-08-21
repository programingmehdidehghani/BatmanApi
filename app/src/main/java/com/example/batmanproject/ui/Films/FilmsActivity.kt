package com.example.batmanproject.ui.Films

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.batmanproject.MyApp
import com.example.batmanproject.R
import com.example.batmanproject.adapter.FilmsAdapter
import com.example.batmanproject.adapter.OnItemClickCallback
import com.example.batmanproject.databinding.ActivityFilmsBinding
import com.example.batmanproject.util.Constant
import com.example.batmanproject.util.Resource
import com.example.batmanproject.util.hasInternetConnection
import com.example.batmanproject.util.toast
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class FilmsActivity : AppCompatActivity() , OnItemClickCallback {

    private val filmViewModel: FilmViewModel by viewModels()

    private var filmsAdapter = FilmsAdapter(this)

    @Inject
    lateinit var sharedPref: SharedPreferences

    private var isInternet: Boolean = false



    private var _binding: ActivityFilmsBinding? = null
    private val viewBinding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityFilmsBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        setUpFilmsRecyclerView()
        if (isInternetAvailable()){
            getAllFilms()
        } else {
            filmViewModel.getFilmsDB().observe(this, Observer { filmDB ->
                Log.i("internet","is off")
                if (filmDB.isNotEmpty()){
                    filmsAdapter.updateList(filmDB)
                } else {
                    toast(this,"there is no data please turn on your internet")
                }
                hideProgress()
            })
        }
    }

    @SuppressLint("ObsoleteSdkInt")
    private fun isInternetAvailable(): Boolean {
        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val networkCapabilities = connectivityManager.activeNetwork ?: return false
            val activeNetwork =
                connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false

            return when {
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }
        } else {
            @Suppress("DEPRECATION")
            val networkInfo = connectivityManager.activeNetworkInfo
            return networkInfo != null && networkInfo.isConnected
        }
    }

    private fun getAllFilms(){
        filmViewModel.getFilms.removeObservers(this)
        filmViewModel.films()
        filmViewModel.getFilms.observe(this,Observer { response ->
            when (response) {
                is Resource.Success -> {
                    Log.i("internet","is on")
                    filmsAdapter.updateList(response.data.Search)
                    hideProgress()
                }
                is Resource.Error -> {
                    toast(this, response.errorMessage)
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
    }
}