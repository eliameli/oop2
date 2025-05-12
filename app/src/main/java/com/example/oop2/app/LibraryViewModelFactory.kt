package com.example.oop2.app

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.oop2.data.local.LibraryDatabase
import com.example.oop2.data.remote.GoogleBooksApi
import com.example.oop2.data.repository.GoogleBooksRepositoryImpl
import com.example.oop2.data.repository.LibraryRepositoryImpl
import com.example.oop2.domain.usecase.AddLibraryItemUseCase
import com.example.oop2.domain.usecase.GetLibraryItemsUseCase
import com.example.oop2.domain.usecase.SearchGoogleBooksUseCase
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class LibraryViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val db = LibraryDatabase.getInstance(context)
        val libraryRepository = LibraryRepositoryImpl(db)

        val googleBooksApi = Retrofit.Builder()
            .baseUrl("https://www.googleapis.com/books/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GoogleBooksApi::class.java)

        val googleBooksRepository = GoogleBooksRepositoryImpl(googleBooksApi)

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
