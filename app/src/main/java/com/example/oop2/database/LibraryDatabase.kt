package com.example.oop2.database

import androidx.room.Database
import androidx.room.RoomDatabase
@Database(entities = [LibraryItemEntity::class], version = 1)
abstract class LibraryDatabase : RoomDatabase() {
    abstract fun libraryDao(): LibraryDao
}