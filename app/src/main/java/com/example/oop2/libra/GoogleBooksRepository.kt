// GoogleBooksRepository.kt
package com.example.oop2.libra

import com.example.oop2.models.Book
import com.example.oop2.models.GoogleBooksResponse
import com.example.oop2.network.GoogleBooksApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.math.absoluteValue

object GoogleBooksRepository {

    private val api: GoogleBooksApi by lazy {
        Retrofit.Builder()
            .baseUrl("https://www.googleapis.com/books/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GoogleBooksApi::class.java)
    }

    suspend fun searchBooks(author: String?, title: String?): List<Book> = withContext(Dispatchers.IO) {
        val query = buildString {
            if (!author.isNullOrBlank()) append("inauthor:$author")
            if (!title.isNullOrBlank()) {
                if (isNotEmpty()) append("+")
                append("intitle:$title")
            }
        }

        val response = api.searchBooks(query = query, maxResults = 20)
        response.items?.mapNotNull { item ->
            val info = item.volumeInfo ?: return@mapNotNull null
            val title = info.title ?: return@mapNotNull null
            val author = info.authors?.joinToString(", ") ?: "Неизвестно"
            val pages = info.pageCount ?: 0
            val isbn = info.industryIdentifiers?.firstOrNull { it.type.contains("ISBN") }?.identifier
                ?: item.id
            Book(
                id = isbn.hashCode().mod(9999).absoluteValue,
                isAvailable = true,
                name = title,
                author = author,
                pages = pages
            )
        } ?: emptyList()
    }
}
