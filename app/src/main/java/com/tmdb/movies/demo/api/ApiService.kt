package com.tmdb.movies.demo.api

import com.tmdb.movies.demo.data.MovieDetail
import com.tmdb.movies.demo.data.MovieImages
import com.tmdb.movies.demo.data.MovieVideos
import com.tmdb.movies.demo.data.Movies
import com.tmdb.movies.demo.utilities.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(
        @Query("page") page: Int = 1,
        @Query("api_key") apiKey: String = API_KEY
    ): Response<Movies>

    @GET("movie/{movie_id}")
    suspend fun getMovieById(
        @Path("movie_id") movie_id: Int?,
        @Query("api_key") apiKey: String = API_KEY
    ): Response<MovieDetail>

    @GET("movie/{movie_id}/images")
    suspend fun getMovieImages(
        @Path("movie_id") movie_id: Int?,
        @Query("api_key") apiKey: String = API_KEY
    ): Response<MovieImages>

    @GET("search/movie")
    suspend fun searchMovie(
        @Query("page") page: Int?,
        @Query("query") movie_id: String?,
        @Query("api_key") apiKey: String = API_KEY
    ): Response<Movies>

    @GET("movie/{movie_id}/videos")
    suspend fun getMovieVideos(
        @Path("movie_id") movie_id: Int?,
        @Query("api_key") apiKey: String = API_KEY
    ): Response<MovieVideos>

}