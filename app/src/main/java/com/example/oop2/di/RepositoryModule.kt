package com.example.oop2.di

import com.example.oop2.data.local.LibraryDatabase
import com.example.oop2.data.remote.GoogleBooksApi
import com.example.oop2.data.repository.GoogleBooksRepositoryImpl
import com.example.oop2.data.repository.LibraryRepositoryImpl
import com.example.oop2.domain.repositoty.GoogleBooksRepository
import com.example.oop2.domain.repositoty.LibraryRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule(
    private val db: LibraryDatabase,
    private val api: GoogleBooksApi
) {
    @Provides
    @Singleton
    fun provideLibraryRepository(): LibraryRepository = LibraryRepositoryImpl(db)

    @Provides
    @Singleton
    fun provideGoogleBooksRepository(): GoogleBooksRepository = GoogleBooksRepositoryImpl(api)
}
