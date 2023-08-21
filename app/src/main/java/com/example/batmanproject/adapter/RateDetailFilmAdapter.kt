package com.example.batmanproject.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.batmanproject.databinding.ItemFilmsBinding
import com.example.batmanproject.databinding.ItemRatingBinding
import com.example.batmanproject.model.DetailFilm


class RateDetailFilmAdapter : RecyclerView.Adapter<RateDetailFilmAdapter.RateDetailFilmViewHolder>() {


    private val rating: ArrayList<DetailFilm.Rating> = arrayListOf()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RateDetailFilmViewHolder {
        val binding = ItemRatingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RateDetailFilmViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return rating.size
    }

    override fun onBindViewHolder(holder: RateDetailFilmViewHolder, position: Int) {
        holder.bind(rating[position])
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(list: List<DetailFilm.Rating>) {
        this.rating.clear()
        this.rating.addAll(list)
        notifyDataSetChanged()
    }


    inner class RateDetailFilmViewHolder(private val binding: ItemRatingBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(model: DetailFilm.Rating) {
            binding.txtSourceInDetailActivity.text = model.Source
            binding.txtValueInDetailActivity.text = model.Value

        }
    }


}