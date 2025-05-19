package com.example.oop2.app

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.oop2.domain.usecase.AddLibraryItemUseCase
import com.example.oop2.domain.usecase.GetLibraryItemsUseCase
import com.example.oop2.domain.usecase.SearchGoogleBooksUseCase


class LibraryViewModelFactory(private val app: LibraryApp) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val libraryRepository = app.appComponent.libraryRepository()
        val googleBooksRepository = app.appComponent.googleBooksRepository()

        val addItemUseCase = AddLibraryItemUseCase(libraryRepository)
        val getItemsUseCase = GetLibraryItemsUseCase(libraryRepository)
        val searchGoogleBooksUseCase = SearchGoogleBooksUseCase(googleBooksRepository)

        @Suppress("UNCHECKED_CAST")
        return LibraryViewModel(
            addItemUseCase,
            getItemsUseCase,
            searchGoogleBooksUseCase
        ) as T
    }
}
