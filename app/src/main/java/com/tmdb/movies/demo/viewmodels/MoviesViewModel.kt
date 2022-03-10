package com.tmdb.movies.demo.viewmodels

import androidx.lifecycle.*
import androidx.paging.PagingData
import com.tmdb.movies.demo.data.MovieDetail
import com.tmdb.movies.demo.data.MovieItem
import com.tmdb.movies.demo.repository.MoviesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MoviesViewModel @Inject constructor(private val repository: MoviesRepository) : ViewModel() {

    private val moviesDetailLiveData = MutableLiveData<MovieDetail?>()

    //movie db functions
    fun insertAllMoviesToDB(movies: List<MovieItem>?) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertAllMoviesToDB(movies)
        }
    }

    fun deleteMoviesFromDB() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteMoviesFromDB()
        }
    }

    fun getMoviesFromDB() = repository.getMoviesFromDB()?.asLiveData()


    //movie apis functions
    fun getUpcomingMovies(): LiveData<PagingData<MovieItem>> {
        return repository.getUpcomingMovies()
    }

    fun getSearchMovies(query: String?): LiveData<PagingData<MovieItem>> {
        return repository.getSearchMovies(query)
    }

    fun getMovieById(movie_id: Int?): MutableLiveData<MovieDetail?> {
        viewModelScope.launch(Dispatchers.IO) {
            val apiResponse = repository.getMovieById(movie_id)
            moviesDetailLiveData.postValue(apiResponse)
        }

        return moviesDetailLiveData
    }


}