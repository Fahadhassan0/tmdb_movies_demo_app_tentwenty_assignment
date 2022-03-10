package com.tmdb.movies.demo.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tmdb.movies.demo.data.MovieItem
import kotlinx.coroutines.flow.Flow

/**
 * The Dao interface for room database queries
 */
@Dao
interface MoviesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllMovies(movies: List<MovieItem>?)

    @Query("DELETE FROM movies")
    suspend fun deleteMovies()

    @Query("SELECT * FROM movies")
    fun getMovies(): Flow<List<MovieItem>?>?

}


