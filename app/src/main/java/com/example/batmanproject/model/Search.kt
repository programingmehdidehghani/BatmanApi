package com.example.batmanproject.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey


@Entity(
    tableName = "films",
    indices = [Index(value = ["imdbID"], unique = true)]
)
data class Search(
    @PrimaryKey(autoGenerate = true)
    var id : Int? = null,
    val Title: String,
    val Year: String,
    val imdbID: String,
    val Type: String,
    val Poster: String
)
