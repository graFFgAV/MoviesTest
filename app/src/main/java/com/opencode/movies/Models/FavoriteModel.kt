package com.opencode.movies.Models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "favorite")
data class FavoriteModel(

    @PrimaryKey
    @ColumnInfo(name = "id")
    var id: Int,

    @ColumnInfo(name = "title")
    var title: String,

    @ColumnInfo(name = "image_url")
    var image_url: String,

    @ColumnInfo(name = "voteAverage")
    var voteAverage: String,

    @ColumnInfo(name = "releaseDate")
    var releaseDate: String

) : Serializable

data class FavoriteModelId(
    @ColumnInfo(name = "id")
    var id: Int
        )
