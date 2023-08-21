package com.example.batmanproject.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey


@Entity(
    tableName = "detailFilm",
    indices = [Index(value = ["imdbID"], unique = true)]
)
data class DetailFilm(
    @PrimaryKey(autoGenerate = true)
    var id : Int? = null,
    val Title: String,
    val Year: String,
    val Rated: String,
    val Released: String,
    val Runtime: String,
    val Genre: String,
    val Director: String,
    val Writer: String,
    val Actors: String,
    val Plot: String,
    val Language: String,
    val Country: String,
    val Awards: String,
    val Poster: String,
    val Ratings: List<Rating>,
    val Metascore: String,
    val imdbRating: String,
    val imdbVotes: String,
    val imdbID: String,
    val Type: String,
    val DVD: String,
    val BoxOffice: String,
    val Production: String,
    val Website: String,
    val Response: String
) {
    data class Rating(
        val Source: String,
        val Value: String
    )


}