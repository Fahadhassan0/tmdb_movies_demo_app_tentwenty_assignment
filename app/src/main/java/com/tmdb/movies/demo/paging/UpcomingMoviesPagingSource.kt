package com.tmdb.movies.demo.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.tmdb.movies.demo.api.ApiService
import com.tmdb.movies.demo.data.MovieItem

import retrofit2.HttpException
import java.io.IOException

class UpcomingMoviesPagingSource(
    private val apiService: ApiService
) : PagingSource<Int, MovieItem>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieItem> {

        val position = params.key ?: DEFAULT_PAGE

        return try {

            val response = apiService.getUpcomingMovies(position)
            val data = response.body()!!.results

            LoadResult.Page(
                data = data,
                prevKey = if (position == DEFAULT_PAGE) null else position - 1,
                nextKey = if (data.isEmpty()) null else position + 1
            )

        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, MovieItem>): Int {
        return 1
    }

    companion object {
        private const val DEFAULT_PAGE = 1
    }

}