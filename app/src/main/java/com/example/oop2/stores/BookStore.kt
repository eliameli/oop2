package com.example.oop2.stores

import com.example.oop2.domain.model.Book

class BookStore : Store<Book> {
    override fun sell(): Book {
        return Book(1, true, "Мастера и Маргарита", "Михаил Булгаков", 400)
    }
}
