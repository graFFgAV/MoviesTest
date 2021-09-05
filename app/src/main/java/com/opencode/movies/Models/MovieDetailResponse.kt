package com.opencode.movies.Models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class MovieResponse(
    @SerializedName("backdrop_path")
    @Expose
    var backdropPath: String,
    @SerializedName("genres")
    @Expose
    var genres: ArrayList<Genre>,
    @SerializedName("id")
    @Expose
    var id: Int,
    @SerializedName("imdb_id")
    @Expose
    var imdbId: String,
    @SerializedName("original_title")
    @Expose
    var originalTitle: String,
    @SerializedName("overview")
    @Expose
    var overview: String,
    @SerializedName("popularity")
    @Expose
    var popularity: Double,
    @SerializedName("poster_path")
    @Expose
    var posterPath: String,
    @SerializedName("release_date")
    @Expose
    var releaseDate: String,
    @SerializedName("runtime")
    @Expose
    var runtime: Int,
    @SerializedName("status")
    @Expose
    var status: String,
    @SerializedName("tagline")
    @Expose
    var tagline: String,
    @SerializedName("title")
    @Expose
    var title: String,
    @SerializedName("vote_average")
    @Expose
    var voteAverage :Double,
    @SerializedName("vote_count")
    @Expose
    var voteCount :Double,

    ): Serializable

data class Genre (
    @SerializedName("name")
    @Expose
    var name: String,
    @SerializedName("id")
    @Expose
    var id: Int

    ): Serializable
