package com.tmdb.movies.demo.api

import android.util.Log
import retrofit2.Response

abstract class SafeApiRequest {

    suspend fun <T: Any> apiRequest(call : suspend() -> Response<T>): T? {

        val response = call.invoke()
        if (response.isSuccessful && response.body() != null) {
            Log.d(TAG, response.body().toString())
            return response.body()!!
        }
        else {
            Log.d(TAG, response.code().toString())
            Log.d(TAG, response.message())
           return null
        }

    }

    companion object {
        private const val TAG = "SafeApiRequest"
    }
}