package com.example.oop2.stores

import com.example.oop2.Store
import com.example.oop2.models.Book

class BookStore : Store<Book> {
    override fun sell(): Book {
        return Book(1, true, "Мастера и Маргарита", "Михаил Булгаков", 400)
    }
}
