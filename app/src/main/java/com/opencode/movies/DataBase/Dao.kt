package com.opencode.movies.DataBase

import androidx.room.*
import androidx.room.Dao
import com.opencode.movies.Models.FavoriteModel
import com.opencode.movies.Models.FavoriteModelId
import io.reactivex.Single

@Dao
interface Dao {

    @get:Query("SELECT * FROM favorite")
    val all: Single<List<FavoriteModel>>

    @Query("SELECT favorite.id FROM favorite WHERE id = :ID")
    fun loadById(ID: Int): Single<FavoriteModelId>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(fm: FavoriteModel)

    @Query("DELETE FROM favorite WHERE id = :id")
    fun deleteById(id: Int)

    @Delete
    fun delete(fm: FavoriteModel?)

    @Update
    fun update(fm: FavoriteModel?)
}