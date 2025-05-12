package com.example.oop2.domain.usecase


import com.example.oop2.domain.model.LibraryItem
import com.example.oop2.domain.repositoty.LibraryRepository

class GetLibraryItemsUseCase(private val repository: LibraryRepository) {
    suspend operator fun invoke(): List<LibraryItem> = repository.getItems()

    suspend fun getSortedByName(limit: Int, offset: Int): List<LibraryItem> =
        repository.getItemsSortedByName(limit, offset)
}
