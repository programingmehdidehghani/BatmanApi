package com.example.batmanproject.ui.DetailMovie

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.batmanproject.R
import com.example.batmanproject.databinding.ActivityDetailFilmBinding
import com.example.batmanproject.databinding.ActivityFilmsBinding

class DetailFilmActivity : AppCompatActivity() {



    private var _binding: ActivityDetailFilmBinding? = null
    private val viewBinding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityDetailFilmBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null;
    }
}