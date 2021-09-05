package com.opencode.movies.Models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class SearchModel(
    @SerializedName("results")
    @Expose
    var results: ArrayList<Result>
): Serializable

data class Result(
    @SerializedName("backdrop_path")
    @Expose
    var backdropPath: String,
    @SerializedName("original_title")
    @Expose
    var originalTitle: String,
    @SerializedName("id")
    @Expose
    var id: Int,
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
    @SerializedName("title")
    @Expose
    var title: String,
    @SerializedName("vote_average")
    @Expose
    var voteAverage:Double
): Serializable