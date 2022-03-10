package com.tmdb.movies.demo.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.tmdb.movies.demo.api.ApiService
import com.tmdb.movies.demo.api.SafeApiRequest
import com.tmdb.movies.demo.data.MovieDetail
import com.tmdb.movies.demo.data.MovieImages
import com.tmdb.movies.demo.data.MovieItem
import com.tmdb.movies.demo.data.MovieVideos
import com.tmdb.movies.demo.database.MoviesDao
import com.tmdb.movies.demo.paging.MoviesSearchPagingSource
import com.tmdb.movies.demo.paging.UpcomingMoviesPagingSource
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MoviesRepository @Inject constructor(
    @ApplicationContext val context: Context,
    private val apiService: ApiService,
    private val moviesDao: MoviesDao
) : SafeApiRequest() {


    suspend fun insertAllMoviesToDB(movies: List<MovieItem>?) = moviesDao.insertAllMovies(movies)

    suspend fun deleteMoviesFromDB() = moviesDao.deleteMovies()

    fun getMoviesFromDB() = moviesDao.getMovies()


    fun getSearchMovies(query: String?): LiveData<PagingData<MovieItem>> {
        return Pager(config = PagingConfig(
            pageSize = 20, maxSize = 100
        ),
            pagingSourceFactory = {
                MoviesSearchPagingSource(apiService, query)
            }
        ).liveData
    }

    fun getUpcomingMovies(): LiveData<PagingData<MovieItem>> {
        return Pager(config = PagingConfig(
            pageSize = 20, maxSize = 100
        ),
            pagingSourceFactory = {
                UpcomingMoviesPagingSource(apiService)
            }
        ).liveData
    }

    suspend fun getMovieById(movie_id: Int?): MovieDetail? {
        return apiRequest { apiService.getMovieById(movie_id) }
    }

    suspend fun getMovieImages(movie_id: Int): MovieImages? {
        return apiRequest { apiService.getMovieImages(movie_id) }
    }

    suspend fun getMovieVideos(movie_id: Int): MovieVideos? {
        return apiRequest { apiService.getMovieVideos(movie_id) }
    }


}