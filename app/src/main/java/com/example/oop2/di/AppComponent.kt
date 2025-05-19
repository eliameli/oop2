package com.example.oop2.di

import com.example.oop2.app.LibraryApp
import com.example.oop2.domain.repositoty.GoogleBooksRepository
import com.example.oop2.domain.repositoty.LibraryRepository
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [RepositoryModule::class])
interface AppComponent {
    fun libraryRepository(): LibraryRepository
    fun googleBooksRepository(): GoogleBooksRepository

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance app: LibraryApp,
            module: RepositoryModule
        ): AppComponent
    }
}
