package com.example.oop2.domain.usecase

import com.example.oop2.domain.model.Book
import com.example.oop2.domain.repositoty.GoogleBooksRepository

class SearchGoogleBooksUseCase(private val repository: GoogleBooksRepository) {
    suspend operator fun invoke(author: String?, title: String?): List<Book> {
        return repository.searchBooks(author, title)
    }
}