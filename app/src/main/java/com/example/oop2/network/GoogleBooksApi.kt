package com.example.oop2.network

import com.example.oop2.models.GoogleBooksResponse
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.Call

interface GoogleBooksApi {
    @GET("volumes")
    suspend fun searchBooks(
        @Query("q") query: String,
        @Query("maxResults") maxResults: Int = 20
    ): GoogleBooksResponse

}
