package com.example.oop2.data.repository

import com.example.oop2.data.remote.GoogleBooksApi
import com.example.oop2.domain.model.Book
import com.example.oop2.domain.repositoty.GoogleBooksRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.math.absoluteValue

class GoogleBooksRepositoryImpl(
    private val api: GoogleBooksApi
) : GoogleBooksRepository {

    override suspend fun searchBooks(author: String?, title: String?): List<Book> = withContext(Dispatchers.IO) {
        val query = buildString {
            if (!author.isNullOrBlank()) append("inauthor:$author")
            if (!title.isNullOrBlank()) {
                if (isNotEmpty()) append("+")
                append("intitle:$title")
            }
        }

        return@withContext try {
            val response = api.searchBooks(query)
            response.items?.mapNotNull { item ->
                val info = item.volumeInfo ?: return@mapNotNull null
                val bookTitle = info.title ?: return@mapNotNull null
                val authorList = info.authors ?: listOf("Неизвестно")
                val pageCount = info.pageCount ?: 0
                val id = item.id

                Book(
                    id = id.hashCode().mod(9999).absoluteValue,
                    isAvailable = true,
                    name = bookTitle,
                    author = authorList.joinToString(", "),
                    pages = pageCount
                )
            } ?: emptyList()
        } catch (e: Exception) {
            emptyList()
        }
    }
}
