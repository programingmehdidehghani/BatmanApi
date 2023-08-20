package com.example.batmanproject.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.batmanproject.databinding.ItemFilmsBinding
import com.example.batmanproject.model.Search
import com.example.batmanproject.util.ImageLoader


interface OnItemClickCallback {
    fun onItemClick(imdbID: String)
}
class FilmsAdapter(private val onItemClickCallback: OnItemClickCallback) : RecyclerView.Adapter<FilmsAdapter.FilmsViewHolder>() {


    private val search: ArrayList<Search> = arrayListOf()



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmsViewHolder {
        val binding = ItemFilmsBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return FilmsViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return search.size
    }

    override fun onBindViewHolder(holder: FilmsViewHolder, position: Int) {
        holder.bind(search[position],onItemClickCallback)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(list: List<Search>) {
        this.search.clear()
        this.search.addAll(list)
        notifyDataSetChanged()
    }




    inner class FilmsViewHolder(private val binding: ItemFilmsBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(model: Search,onItemClickCallback: OnItemClickCallback) {
            binding.txtTitleInItemFilm.text = model.Title
            binding.txtYearInItemFilm.text = model.Year
            binding.txtTypeInItemFilm.text = model.Type
            ImageLoader.loadImage(
                binding.ivPosterInItemFilm,
                model.Poster
            )

            itemView.setOnClickListener {
                onItemClickCallback.onItemClick(
                    model.imdbID
                )
            }
        }
    }
}