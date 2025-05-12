package com.example.oop2.domain.usecase

import com.example.oop2.domain.model.LibraryItem
import com.example.oop2.domain.repositoty.LibraryRepository
class AddLibraryItemUseCase(private val repository: LibraryRepository) {
    suspend operator fun invoke(item: LibraryItem) {
        repository.addItem(item)
    }
}
