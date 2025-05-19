package com.example.oop2.app

import android.app.Application
import com.example.oop2.data.local.LibraryDatabase
import com.example.oop2.data.remote.GoogleBooksApi
import com.example.oop2.di.AppComponent
import com.example.oop2.di.DaggerAppComponent
import com.example.oop2.di.RepositoryModule
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class LibraryApp : Application() {
    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()

        val db = LibraryDatabase.getInstance(this)

        val api = Retrofit.Builder()
            .baseUrl("https://www.googleapis.com/books/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GoogleBooksApi::class.java)

        appComponent = DaggerAppComponent.factory().create(this, RepositoryModule(db, api))
    }
}
