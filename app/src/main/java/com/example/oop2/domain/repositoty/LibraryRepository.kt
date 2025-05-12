package com.example.oop2.domain.repositoty

import com.example.oop2.domain.model.LibraryItem

interface LibraryRepository {
    suspend fun getItems(): List<LibraryItem>
    suspend fun addItem(item: LibraryItem)
    suspend fun getItemsSortedByName(limit: Int, offset: Int): List<LibraryItem>
    suspend fun getItemsSortedByDate(limit: Int, offset: Int): List<LibraryItem>
}
