package com.opencode.movies.Models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class CreditsResponse(

    @SerializedName("id")
    @Expose
    var id: Int,
    @SerializedName("cast")
    @Expose
    var cast: List<Cast>,
    @SerializedName("crew")
    @Expose
    var crew: List<Crew>
): Serializable

data class Cast(
    @SerializedName("known_for_department")
    @Expose
    var knownForDepartment: String,
    @SerializedName("name")
    @Expose
    var name:String,
    @SerializedName("original_name")
    @Expose
    var originalName: String,
    @SerializedName("popularity")
    @Expose
    var popularity: Double,
    @SerializedName("character")
    @Expose
    var character:String,
    @SerializedName("order")
    @Expose
    var order: Int,
)
data class Crew(
    @SerializedName("name")
    @Expose
    var name: String ,
    @SerializedName("original_name")
    @Expose
    var originalName: String ,
    @SerializedName("department")
    @Expose
    var department: String ,
    @SerializedName("job")
    @Expose
    var job: String
)