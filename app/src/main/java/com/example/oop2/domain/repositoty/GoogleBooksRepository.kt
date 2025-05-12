package com.example.oop2.domain.repositoty

import com.example.oop2.domain.model.Book

interface GoogleBooksRepository {
    suspend fun searchBooks(author: String?, title: String?): List<Book>
}
